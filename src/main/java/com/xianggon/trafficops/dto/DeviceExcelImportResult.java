package com.xianggon.trafficops.dto;

import java.util.List;

public record DeviceExcelImportResult(
        int createdCount,
        int updatedCount,
        int skippedCount,
        List<String> errors) {
}
