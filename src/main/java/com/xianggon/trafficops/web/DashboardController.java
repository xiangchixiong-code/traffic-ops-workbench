package com.xianggon.trafficops.web;

import com.xianggon.trafficops.common.ApiResponse;
import com.xianggon.trafficops.dto.DashboardResponse;
import com.xianggon.trafficops.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "驾驶舱统计", description = "路段、设备、工单状态的汇总指标")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @Operation(summary = "查询首页统计数据")
    public ApiResponse<DashboardResponse> summary() {
        return ApiResponse.ok(dashboardService.summary());
    }
}
