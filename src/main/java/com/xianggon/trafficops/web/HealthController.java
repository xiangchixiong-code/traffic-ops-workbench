package com.xianggon.trafficops.web;

import com.xianggon.trafficops.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "健康检查", description = "用于验证服务是否启动成功")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "健康检查")
    public ApiResponse<Map<String, Object>> health() {
        return ApiResponse.ok(Map.of(
                "service", "traffic-ops-workbench",
                "status", "UP"));
    }
}
