package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.DeviceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeviceStatusUpdateRequest(
        @NotNull DeviceStatus status,
        @NotBlank String operator) {
}
