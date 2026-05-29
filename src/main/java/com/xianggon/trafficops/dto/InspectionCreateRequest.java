package com.xianggon.trafficops.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "新增巡检记录请求")
public record InspectionCreateRequest(
        @Schema(description = "关联设备 ID", example = "1")
        @NotNull Long deviceId,
        @Schema(description = "巡检人", example = "曹博轩")
        @NotBlank String inspector,
        @Schema(description = "巡检时间，格式 yyyy-MM-dd'T'HH:mm:ss", example = "2026-05-30T09:30:00")
        @NotNull LocalDateTime inspectedAt,
        @Schema(description = "巡检结果，例如 PASS 正常、WARN 异常提醒", example = "PASS")
        @NotBlank String result,
        @Schema(description = "巡检备注", example = "镜头画面清晰，角度正常。")
        @NotBlank String notes) {
}
