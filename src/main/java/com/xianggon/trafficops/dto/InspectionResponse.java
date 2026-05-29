package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.InspectionRecord;
import java.time.LocalDateTime;

public record InspectionResponse(
        Long id,
        Long deviceId,
        String deviceName,
        String roadSectionName,
        String inspector,
        LocalDateTime inspectedAt,
        String result,
        String notes) {

    public static InspectionResponse from(InspectionRecord inspectionRecord) {
        return new InspectionResponse(
                inspectionRecord.getId(),
                inspectionRecord.getDeviceAsset().getId(),
                inspectionRecord.getDeviceAsset().getName(),
                inspectionRecord.getDeviceAsset().getRoadSection().getName(),
                inspectionRecord.getInspector(),
                inspectionRecord.getInspectedAt(),
                inspectionRecord.getResult(),
                inspectionRecord.getNotes());
    }
}
