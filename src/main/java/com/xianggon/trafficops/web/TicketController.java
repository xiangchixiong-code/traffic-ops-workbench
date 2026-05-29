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
public class TicketController {

    private final TicketService ticketService;
    private final InspectionService inspectionService;

    public TicketController(TicketService ticketService, InspectionService inspectionService) {
        this.ticketService = ticketService;
        this.inspectionService = inspectionService;
    }

    @GetMapping("/tickets")
    public ApiResponse<List<TicketResponse>> search(@RequestParam(required = false) TicketStatus status) {
        return ApiResponse.ok(ticketService.search(status));
    }

    @PostMapping("/tickets")
    public ApiResponse<TicketResponse> create(@Valid @RequestBody TicketCreateRequest request) {
        return ApiResponse.ok(ticketService.create(request));
    }

    @PatchMapping("/tickets/{id}/status")
    public ApiResponse<TicketResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody TicketStatusUpdateRequest request) {
        return ApiResponse.ok(ticketService.updateStatus(id, request));
    }

    @GetMapping("/inspections")
    public ApiResponse<List<InspectionResponse>> recentInspections() {
        return ApiResponse.ok(inspectionService.recent());
    }

    @PostMapping("/inspections")
    public ApiResponse<InspectionResponse> createInspection(@Valid @RequestBody InspectionCreateRequest request) {
        return ApiResponse.ok(inspectionService.create(request));
    }
}
