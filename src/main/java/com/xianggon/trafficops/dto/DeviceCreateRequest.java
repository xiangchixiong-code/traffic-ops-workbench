package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.DeviceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record DeviceCreateRequest(
        @NotBlank String name,
        @NotBlank String assetCode,
        @NotBlank String deviceType,
        @NotNull DeviceStatus status,
        @NotNull Long roadSectionId,
        @NotBlank String location,
        @NotNull LocalDate installedAt) {
}
