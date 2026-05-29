package com.xianggon.trafficops.web;

import com.xianggon.trafficops.common.ApiResponse;
import com.xianggon.trafficops.dto.DeviceCreateRequest;
import com.xianggon.trafficops.dto.DeviceResponse;
import com.xianggon.trafficops.dto.DeviceStatusUpdateRequest;
import com.xianggon.trafficops.domain.DeviceStatus;
import com.xianggon.trafficops.service.DeviceService;
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
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public ApiResponse<List<DeviceResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) DeviceStatus status,
            @RequestParam(required = false) Long roadSectionId) {
        return ApiResponse.ok(deviceService.search(keyword, status, roadSectionId));
    }

    @PostMapping
    public ApiResponse<DeviceResponse> create(@Valid @RequestBody DeviceCreateRequest request) {
        return ApiResponse.ok(deviceService.create(request));
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<DeviceResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody DeviceStatusUpdateRequest request) {
        return ApiResponse.ok(deviceService.updateStatus(id, request));
    }
}
