package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.OperationLog;
import java.time.LocalDateTime;

public record OperationLogResponse(
        Long id,
        String operator,
        String targetType,
        Long targetId,
        String action,
        String detail,
        LocalDateTime createdAt) {

    public static OperationLogResponse from(OperationLog operationLog) {
        return new OperationLogResponse(
                operationLog.getId(),
                operationLog.getOperator(),
                operationLog.getTargetType(),
                operationLog.getTargetId(),
                operationLog.getAction(),
                operationLog.getDetail(),
                operationLog.getCreatedAt());
    }
}
