package com.xianggon.trafficops.service;

import com.xianggon.trafficops.domain.DeviceAsset;
import com.xianggon.trafficops.domain.DeviceStatus;
import com.xianggon.trafficops.domain.RoadSection;
import com.xianggon.trafficops.dto.DeviceExcelImportResult;
import com.xianggon.trafficops.repository.DeviceAssetRepository;
import com.xianggon.trafficops.repository.RoadSectionRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DeviceExcelService {

    private static final DataFormatter DATA_FORMATTER = new DataFormatter(Locale.ROOT);
    private static final String[] HEADERS = {
            "设备名称", "资产编号", "设备类型", "状态", "路段编码", "路段名称", "区域", "安装位置", "安装日期"
    };

    private final DeviceAssetRepository deviceAssetRepository;
    private final RoadSectionRepository roadSectionRepository;
    private final OperationLogService operationLogService;

    public DeviceExcelService(DeviceAssetRepository deviceAssetRepository,
            RoadSectionRepository roadSectionRepository,
            OperationLogService operationLogService) {
        this.deviceAssetRepository = deviceAssetRepository;
        this.roadSectionRepository = roadSectionRepository;
        this.operationLogService = operationLogService;
    }

    @Transactional(readOnly = true)
    public byte[] exportDevices(String keyword, DeviceStatus status, Long roadSectionId) {
        List<DeviceAsset> devices = deviceAssetRepository.search(keyword, status, roadSectionId);

        try (Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("device-assets");
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                headerRow.createCell(i).setCellValue(HEADERS[i]);
            }

            int rowIndex = 1;
            for (DeviceAsset device : devices) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(device.getName());
                row.createCell(1).setCellValue(device.getAssetCode());
                row.createCell(2).setCellValue(device.getDeviceType());
                row.createCell(3).setCellValue(device.getStatus().name());
                row.createCell(4).setCellValue(device.getRoadSection().getCode());
                row.createCell(5).setCellValue(device.getRoadSection().getName());
                row.createCell(6).setCellValue(device.getRoadSection().getRegion());
                row.createCell(7).setCellValue(device.getLocation());
                row.createCell(8).setCellValue(device.getInstalledAt().toString());
            }

            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException("export device excel failed", exception);
        }
    }

    @Transactional
    public DeviceExcelImportResult importDevices(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("上传的 Excel 文件不能为空");
        }

        int createdCount = 0;
        int updatedCount = 0;
        int skippedCount = 0;
        List<String> errors = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            if (workbook.getNumberOfSheets() == 0) {
                throw new IllegalArgumentException("Excel 文件中没有工作表");
            }

            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (isBlankRow(row)) {
                    continue;
                }

                try {
                    DeviceExcelRow excelRow = readDeviceRow(row);
                    RoadSection roadSection = roadSectionRepository.findByCode(excelRow.roadSectionCode())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "路段编码不存在：" + excelRow.roadSectionCode()));

                    Optional<DeviceAsset> existingDevice = deviceAssetRepository.findByAssetCode(excelRow.assetCode());
                    if (existingDevice.isPresent()) {
                        DeviceAsset saved = existingDevice.get();
                        saved.updateBasicInfo(excelRow.name(), excelRow.deviceType(), excelRow.status(),
                                roadSection, excelRow.location(), excelRow.installedAt());
                        deviceAssetRepository.save(saved);
                        updatedCount++;
                        operationLogService.save("excel-import", "device", saved.getId(), "EXCEL_IMPORT_UPDATE",
                                "通过 Excel 导入更新设备 " + saved.getAssetCode());
                    } else {
                        DeviceAsset saved = deviceAssetRepository.save(new DeviceAsset(
                                excelRow.name(),
                                excelRow.assetCode(),
                                excelRow.deviceType(),
                                excelRow.status(),
                                roadSection,
                                excelRow.location(),
                                excelRow.installedAt()));
                        createdCount++;
                        operationLogService.save("excel-import", "device", saved.getId(), "EXCEL_IMPORT_CREATE",
                                "通过 Excel 导入新增设备 " + saved.getAssetCode());
                    }
                } catch (IllegalArgumentException exception) {
                    skippedCount++;
                    errors.add("第 " + (rowIndex + 1) + " 行：" + exception.getMessage());
                }
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("无法读取 Excel 文件，请确认文件格式为 .xlsx 或 .xls");
        }

        return new DeviceExcelImportResult(createdCount, updatedCount, skippedCount, List.copyOf(errors));
    }

    private DeviceExcelRow readDeviceRow(Row row) {
        String name = requiredString(row, 0, "设备名称");
        String assetCode = requiredString(row, 1, "资产编号");
        String deviceType = requiredString(row, 2, "设备类型");
        DeviceStatus status = parseStatus(requiredString(row, 3, "状态"));
        String roadSectionCode = requiredString(row, 4, "路段编码");
        String location = requiredString(row, 7, "安装位置");
        LocalDate installedAt = parseDate(row.getCell(8), "安装日期");

        return new DeviceExcelRow(name, assetCode, deviceType, status, roadSectionCode, location, installedAt);
    }

    private DeviceStatus parseStatus(String value) {
        try {
            return DeviceStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("状态只能是 ONLINE、WARNING、OFFLINE、MAINTENANCE、FAULT");
        }
    }

    private LocalDate parseDate(Cell cell, String fieldName) {
        if (cell == null) {
            throw new IllegalArgumentException(fieldName + "不能为空");
        }
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        }

        String value = cellText(cell);
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + "不能为空");
        }
        try {
            return LocalDate.parse(value);
        } catch (RuntimeException exception) {
            throw new IllegalArgumentException(fieldName + "格式应为 yyyy-MM-dd");
        }
    }

    private String requiredString(Row row, int cellIndex, String fieldName) {
        String value = cellText(row.getCell(cellIndex));
        if (value.isBlank()) {
            throw new IllegalArgumentException(fieldName + "不能为空");
        }
        return value;
    }

    private boolean isBlankRow(Row row) {
        if (row == null) {
            return true;
        }
        for (int i = 0; i < HEADERS.length; i++) {
            if (!cellText(row.getCell(i)).isBlank()) {
                return false;
            }
        }
        return true;
    }

    private String cellText(Cell cell) {
        if (cell == null) {
            return "";
        }
        return DATA_FORMATTER.formatCellValue(cell).trim();
    }

    private record DeviceExcelRow(
            String name,
            String assetCode,
            String deviceType,
            DeviceStatus status,
            String roadSectionCode,
            String location,
            LocalDate installedAt) {
    }
}
