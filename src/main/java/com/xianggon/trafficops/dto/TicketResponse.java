package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.FaultTicket;
import com.xianggon.trafficops.domain.TicketPriority;
import com.xianggon.trafficops.domain.TicketStatus;
import java.time.LocalDateTime;

public record TicketResponse(
        Long id,
        Long deviceId,
        String deviceName,
        String roadSectionName,
        String title,
        String description,
        TicketPriority priority,
        TicketStatus status,
        String reporter,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt) {

    public static TicketResponse from(FaultTicket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getDeviceAsset().getId(),
                ticket.getDeviceAsset().getName(),
                ticket.getDeviceAsset().getRoadSection().getName(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getPriority(),
                ticket.getStatus(),
                ticket.getReporter(),
                ticket.getCreatedAt(),
                ticket.getResolvedAt());
    }
}
