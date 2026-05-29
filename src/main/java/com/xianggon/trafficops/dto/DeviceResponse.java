package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.DeviceAsset;
import com.xianggon.trafficops.domain.DeviceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "设备资产返回数据")
public record DeviceResponse(
        @Schema(description = "设备 ID", example = "1")
        Long id,
        @Schema(description = "设备名称", example = "收费站视频监控摄像头")
        String name,
        @Schema(description = "资产编号", example = "CAM-YC-001")
        String assetCode,
        @Schema(description = "设备类型", example = "Camera")
        String deviceType,
        @Schema(description = "设备状态", example = "ONLINE")
        DeviceStatus status,
        @Schema(description = "所属路段名称", example = "银川北收费站至永宁段")
        String roadSectionName,
        @Schema(description = "所属区域", example = "银川")
        String region,
        @Schema(description = "安装位置", example = "银川北收费站入口")
        String location,
        @Schema(description = "安装日期", example = "2024-08-12")
        LocalDate installedAt) {

    public static DeviceResponse from(DeviceAsset deviceAsset) {
        return new DeviceResponse(
                deviceAsset.getId(),
                deviceAsset.getName(),
                deviceAsset.getAssetCode(),
                deviceAsset.getDeviceType(),
                deviceAsset.getStatus(),
                deviceAsset.getRoadSection().getName(),
                deviceAsset.getRoadSection().getRegion(),
                deviceAsset.getLocation(),
                deviceAsset.getInstalledAt());
    }
}
