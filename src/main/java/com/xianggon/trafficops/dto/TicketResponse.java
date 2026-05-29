package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.FaultTicket;
import com.xianggon.trafficops.domain.TicketPriority;
import com.xianggon.trafficops.domain.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "故障工单返回数据")
public record TicketResponse(
        @Schema(description = "工单 ID", example = "1")
        Long id,
        @Schema(description = "关联设备 ID", example = "2")
        Long deviceId,
        @Schema(description = "关联设备名称", example = "路面温湿度传感器")
        String deviceName,
        @Schema(description = "所属路段名称", example = "石嘴山西互通段")
        String roadSectionName,
        @Schema(description = "故障标题", example = "传感器数据波动")
        String title,
        @Schema(description = "故障描述")
        String description,
        @Schema(description = "优先级", example = "HIGH")
        TicketPriority priority,
        @Schema(description = "工单状态：OPEN 待处理，IN_PROGRESS 处理中，RESOLVED 已解决，CLOSED 已关闭",
                example = "OPEN")
        TicketStatus status,
        @Schema(description = "报修人或来源", example = "运维值班员")
        String reporter,
        @Schema(description = "创建时间")
        LocalDateTime createdAt,
        @Schema(description = "解决时间，未解决时为空")
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
