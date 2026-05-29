package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.DeviceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "更新设备状态请求")
public record DeviceStatusUpdateRequest(
        @Schema(description = "目标设备状态：ONLINE 在线，WARNING 告警，OFFLINE 离线，MAINTENANCE 维护中，FAULT 故障",
                example = "MAINTENANCE")
        @NotNull DeviceStatus status,
        @Schema(description = "操作人", example = "运维值班员")
        @NotBlank String operator) {
}
