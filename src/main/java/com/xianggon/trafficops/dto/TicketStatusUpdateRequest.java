package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "更新工单状态请求")
public record TicketStatusUpdateRequest(
        @Schema(description = "目标工单状态：OPEN 待处理，IN_PROGRESS 处理中，RESOLVED 已解决，CLOSED 已关闭",
                example = "IN_PROGRESS")
        @NotNull TicketStatus status,
        @Schema(description = "操作人", example = "运维值班员")
        @NotBlank String operator) {
}
