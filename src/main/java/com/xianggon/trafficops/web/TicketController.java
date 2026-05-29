package com.xianggon.trafficops.web;

import com.xianggon.trafficops.common.ApiResponse;
import com.xianggon.trafficops.domain.TicketStatus;
import com.xianggon.trafficops.dto.InspectionCreateRequest;
import com.xianggon.trafficops.dto.InspectionResponse;
import com.xianggon.trafficops.dto.TicketCreateRequest;
import com.xianggon.trafficops.dto.TicketResponse;
import com.xianggon.trafficops.dto.TicketStatusUpdateRequest;
import com.xianggon.trafficops.service.InspectionService;
import com.xianggon.trafficops.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "工单与巡检", description = "故障工单流转和设备巡检记录")
public class TicketController {

    private final TicketService ticketService;
    private final InspectionService inspectionService;

    public TicketController(TicketService ticketService, InspectionService inspectionService) {
        this.ticketService = ticketService;
        this.inspectionService = inspectionService;
    }

    @GetMapping("/tickets")
    @Operation(summary = "查询故障工单", description = "可按工单状态筛选")
    public ApiResponse<List<TicketResponse>> search(@RequestParam(required = false) TicketStatus status) {
        return ApiResponse.ok(ticketService.search(status));
    }

    @PostMapping("/tickets")
    @Operation(summary = "新建故障工单")
    public ApiResponse<TicketResponse> create(@Valid @RequestBody TicketCreateRequest request) {
        return ApiResponse.ok(ticketService.create(request));
    }

    @PatchMapping("/tickets/{id}/status")
    @Operation(summary = "更新工单状态", description = "用于模拟派单、处理中、已解决、关闭等流转")
    public ApiResponse<TicketResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody TicketStatusUpdateRequest request) {
        return ApiResponse.ok(ticketService.updateStatus(id, request));
    }

    @GetMapping("/inspections")
    @Operation(summary = "查询最近巡检记录")
    public ApiResponse<List<InspectionResponse>> recentInspections() {
        return ApiResponse.ok(inspectionService.recent());
    }

    @PostMapping("/inspections")
    @Operation(summary = "新增巡检记录")
    public ApiResponse<InspectionResponse> createInspection(@Valid @RequestBody InspectionCreateRequest request) {
        return ApiResponse.ok(inspectionService.create(request));
    }
}
