package com.xianggon.trafficops.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;

@Schema(description = "驾驶舱统计返回数据")
public record DashboardResponse(
        @Schema(description = "路段总数", example = "3")
        long totalRoadSections,
        @Schema(description = "设备总数", example = "3")
        long totalDevices,
        @Schema(description = "工单总数", example = "2")
        long totalTickets,
        @Schema(description = "按设备状态统计数量")
        Map<String, Long> devicesByStatus,
        @Schema(description = "按工单状态统计数量")
        Map<String, Long> ticketsByStatus) {
}
