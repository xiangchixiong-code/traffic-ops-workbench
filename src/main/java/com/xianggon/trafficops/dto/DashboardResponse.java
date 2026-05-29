package com.xianggon.trafficops.dto;

import java.util.Map;

public record DashboardResponse(
        long totalRoadSections,
        long totalDevices,
        long totalTickets,
        Map<String, Long> devicesByStatus,
        Map<String, Long> ticketsByStatus) {
}
