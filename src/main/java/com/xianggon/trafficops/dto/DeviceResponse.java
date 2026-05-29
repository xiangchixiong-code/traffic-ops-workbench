package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.DeviceAsset;
import com.xianggon.trafficops.domain.DeviceStatus;
import java.time.LocalDate;

public record DeviceResponse(
        Long id,
        String name,
        String assetCode,
        String deviceType,
        DeviceStatus status,
        String roadSectionName,
        String region,
        String location,
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
