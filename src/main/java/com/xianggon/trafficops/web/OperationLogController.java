package com.xianggon.trafficops.web;

import com.xianggon.trafficops.common.ApiResponse;
import com.xianggon.trafficops.dto.OperationLogResponse;
import com.xianggon.trafficops.service.OperationLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operation-logs")
@Tag(name = "操作日志", description = "记录设备、工单、巡检等关键操作")
public class OperationLogController {

    private final OperationLogService operationLogService;

    public OperationLogController(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @GetMapping
    @Operation(summary = "查询最近操作日志")
    public ApiResponse<List<OperationLogResponse>> recentLogs() {
        return ApiResponse.ok(operationLogService.recentLogs().stream()
                .map(OperationLogResponse::from)
                .toList());
    }
}
