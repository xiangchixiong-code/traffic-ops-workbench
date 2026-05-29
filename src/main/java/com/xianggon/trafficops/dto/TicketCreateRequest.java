package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.TicketPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "新建故障工单请求")
public record TicketCreateRequest(
        @Schema(description = "关联设备 ID", example = "2")
        @NotNull Long deviceId,
        @Schema(description = "故障标题", example = "传感器采集数据波动")
        @NotBlank String title,
        @Schema(description = "故障描述", example = "夜间采集值波动较大，需要复核采集链路和供电情况。")
        @NotBlank String description,
        @Schema(description = "优先级：LOW 低，MEDIUM 中，HIGH 高，URGENT 紧急", example = "HIGH")
        @NotNull TicketPriority priority,
        @Schema(description = "报修人或来源", example = "调度中心")
        @NotBlank String reporter) {
}
