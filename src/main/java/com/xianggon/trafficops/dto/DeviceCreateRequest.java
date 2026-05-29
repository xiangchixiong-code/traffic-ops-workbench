package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.DeviceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "新增设备资产请求")
public record DeviceCreateRequest(
        @Schema(description = "设备名称", example = "收费站入口抓拍摄像机")
        @NotBlank String name,
        @Schema(description = "资产编号，设备台账中的唯一编码", example = "CAM-YC-009")
        @NotBlank String assetCode,
        @Schema(description = "设备类型", example = "Camera")
        @NotBlank String deviceType,
        @Schema(description = "设备状态：ONLINE 在线，WARNING 告警，OFFLINE 离线，MAINTENANCE 维护中，FAULT 故障",
                example = "ONLINE")
        @NotNull DeviceStatus status,
        @Schema(description = "所属路段 ID，可通过路段基础数据接口查询", example = "1")
        @NotNull Long roadSectionId,
        @Schema(description = "设备安装位置", example = "银川北收费站入口车道")
        @NotBlank String location,
        @Schema(description = "安装日期，格式 yyyy-MM-dd", example = "2025-03-01")
        @NotNull LocalDate installedAt) {
}
