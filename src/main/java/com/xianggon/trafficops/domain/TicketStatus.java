package com.xianggon.trafficops.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "工单状态：OPEN 待处理，IN_PROGRESS 处理中，RESOLVED 已解决，CLOSED 已关闭")
public enum TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED
}
