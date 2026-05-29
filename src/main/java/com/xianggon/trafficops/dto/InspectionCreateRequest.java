package com.xianggon.trafficops.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record InspectionCreateRequest(
        @NotNull Long deviceId,
        @NotBlank String inspector,
        @NotNull LocalDateTime inspectedAt,
        @NotBlank String result,
        @NotBlank String notes) {
}
