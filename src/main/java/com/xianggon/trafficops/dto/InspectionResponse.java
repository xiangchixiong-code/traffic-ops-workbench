package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.InspectionRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "巡检记录返回数据")
public record InspectionResponse(
        @Schema(description = "巡检记录 ID", example = "1")
        Long id,
        @Schema(description = "关联设备 ID", example = "1")
        Long deviceId,
        @Schema(description = "设备名称", example = "收费站视频监控摄像头")
        String deviceName,
        @Schema(description = "所属路段名称", example = "银川北收费站至永宁段")
        String roadSectionName,
        @Schema(description = "巡检人", example = "曹博轩")
        String inspector,
        @Schema(description = "巡检时间")
        LocalDateTime inspectedAt,
        @Schema(description = "巡检结果", example = "PASS")
        String result,
        @Schema(description = "巡检备注", example = "镜头画面清晰，角度正常。")
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
