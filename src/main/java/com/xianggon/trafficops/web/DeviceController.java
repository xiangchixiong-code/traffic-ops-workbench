package com.xianggon.trafficops.web;

import com.xianggon.trafficops.common.ApiResponse;
import com.xianggon.trafficops.domain.DeviceStatus;
import com.xianggon.trafficops.dto.DeviceCreateRequest;
import com.xianggon.trafficops.dto.DeviceExcelImportResult;
import com.xianggon.trafficops.dto.DeviceResponse;
import com.xianggon.trafficops.dto.DeviceStatusUpdateRequest;
import com.xianggon.trafficops.service.DeviceExcelService;
import com.xianggon.trafficops.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/devices")
@Tag(name = "设备台账", description = "交通信息化设备资产的查询、维护、Excel 导入导出")
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceExcelService deviceExcelService;

    public DeviceController(DeviceService deviceService, DeviceExcelService deviceExcelService) {
        this.deviceService = deviceService;
        this.deviceExcelService = deviceExcelService;
    }

    @GetMapping
    @Operation(summary = "查询设备台账", description = "支持按设备名称/资产编号、设备状态、路段筛选")
    public ApiResponse<List<DeviceResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) DeviceStatus status,
            @RequestParam(required = false) Long roadSectionId) {
        return ApiResponse.ok(deviceService.search(keyword, status, roadSectionId));
    }

    @PostMapping
    @Operation(summary = "新增设备资产")
    public ApiResponse<DeviceResponse> create(@Valid @RequestBody DeviceCreateRequest request) {
        return ApiResponse.ok(deviceService.create(request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "更新设备状态", description = "用于记录设备在线、告警、故障、维护等状态变化")
    public ApiResponse<DeviceResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody DeviceStatusUpdateRequest request) {
        return ApiResponse.ok(deviceService.updateStatus(id, request));
    }

    @GetMapping(value = "/export", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Operation(summary = "导出设备台账 Excel", description = "导出的表格可直接作为设备台账模板再导入")
    public ResponseEntity<byte[]> exportDevices(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) DeviceStatus status,
            @RequestParam(required = false) Long roadSectionId) {
        byte[] content = deviceExcelService.exportDevices(keyword, status, roadSectionId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=device-assets.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(content);
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "导入设备台账 Excel", description = "按资产编号判断新增或更新，路段编码需来自 /api/road-sections")
    public ApiResponse<DeviceExcelImportResult> importDevices(@RequestPart("file") MultipartFile file) {
        return ApiResponse.ok(deviceExcelService.importDevices(file));
    }
}
