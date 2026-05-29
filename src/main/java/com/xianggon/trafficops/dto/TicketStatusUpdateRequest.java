package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketStatusUpdateRequest(
        @NotNull TicketStatus status,
        @NotBlank String operator) {
}
