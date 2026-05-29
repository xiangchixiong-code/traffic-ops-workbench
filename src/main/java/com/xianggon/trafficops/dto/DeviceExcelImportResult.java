package com.xianggon.trafficops.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "设备台账 Excel 导入结果")
public record DeviceExcelImportResult(
        @Schema(description = "新增设备数量", example = "1")
        int createdCount,
        @Schema(description = "更新设备数量", example = "0")
        int updatedCount,
        @Schema(description = "跳过行数量", example = "0")
        int skippedCount,
        @Schema(description = "导入失败的行说明")
        List<String> errors) {
}
