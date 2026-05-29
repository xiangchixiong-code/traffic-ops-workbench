package com.xianggon.trafficops.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "设备状态：ONLINE 在线，WARNING 告警，OFFLINE 离线，MAINTENANCE 维护中，FAULT 故障")
public enum DeviceStatus {
    ONLINE,
    WARNING,
    OFFLINE,
    MAINTENANCE,
    FAULT
}
