package com.xianggon.trafficops.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "工单优先级：LOW 低，MEDIUM 中，HIGH 高，URGENT 紧急")
public enum TicketPriority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
}
