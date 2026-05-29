package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketCreateRequest(
        @NotNull Long deviceId,
        @NotBlank String title,
        @NotBlank String description,
        @NotNull TicketPriority priority,
        @NotBlank String reporter) {
}
