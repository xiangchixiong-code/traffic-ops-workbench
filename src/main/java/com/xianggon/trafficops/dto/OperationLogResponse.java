package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.OperationLog;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "操作日志返回数据")
public record OperationLogResponse(
        @Schema(description = "日志 ID", example = "1")
        Long id,
        @Schema(description = "操作人", example = "system")
        String operator,
        @Schema(description = "操作对象类型，例如 device、ticket、inspection", example = "device")
        String targetType,
        @Schema(description = "操作对象 ID", example = "1")
        Long targetId,
        @Schema(description = "操作动作", example = "CREATE")
        String action,
        @Schema(description = "操作详情", example = "created asset CAM-YC-001")
        String detail,
        @Schema(description = "操作时间")
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
