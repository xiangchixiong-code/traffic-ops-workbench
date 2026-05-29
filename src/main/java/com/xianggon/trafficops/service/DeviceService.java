package com.xianggon.trafficops.service;

import com.xianggon.trafficops.common.ResourceNotFoundException;
import com.xianggon.trafficops.domain.DeviceAsset;
import com.xianggon.trafficops.domain.DeviceStatus;
import com.xianggon.trafficops.domain.RoadSection;
import com.xianggon.trafficops.dto.DeviceCreateRequest;
import com.xianggon.trafficops.dto.DeviceResponse;
import com.xianggon.trafficops.dto.DeviceStatusUpdateRequest;
import com.xianggon.trafficops.repository.DeviceAssetRepository;
import com.xianggon.trafficops.repository.RoadSectionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceService {

    private final DeviceAssetRepository deviceAssetRepository;
    private final RoadSectionRepository roadSectionRepository;
    private final OperationLogService operationLogService;

    public DeviceService(DeviceAssetRepository deviceAssetRepository,
            RoadSectionRepository roadSectionRepository,
            OperationLogService operationLogService) {
        this.deviceAssetRepository = deviceAssetRepository;
        this.roadSectionRepository = roadSectionRepository;
        this.operationLogService = operationLogService;
    }

    @Transactional(readOnly = true)
    public List<DeviceResponse> search(String keyword, DeviceStatus status, Long roadSectionId) {
        return deviceAssetRepository.search(keyword, status, roadSectionId).stream()
                .map(DeviceResponse::from)
                .toList();
    }

    @Transactional
    public DeviceResponse create(DeviceCreateRequest request) {
        RoadSection roadSection = roadSectionRepository.findById(request.roadSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("road section not found: " + request.roadSectionId()));

        DeviceAsset deviceAsset = new DeviceAsset(
                request.name(),
                request.assetCode(),
                request.deviceType(),
                request.status(),
                roadSection,
                request.location(),
                request.installedAt());

        DeviceAsset saved = deviceAssetRepository.save(deviceAsset);
        operationLogService.save("system", "device", saved.getId(), "CREATE",
                "created asset " + saved.getAssetCode() + " on section " + roadSection.getCode());
        return DeviceResponse.from(saved);
    }

    @Transactional
    public DeviceResponse updateStatus(Long id, DeviceStatusUpdateRequest request) {
        DeviceAsset deviceAsset = deviceAssetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("device not found: " + id));
        deviceAsset.updateStatus(request.status());
        operationLogService.save(request.operator(), "device", id, "STATUS_UPDATE",
                "status updated to " + request.status());
        return DeviceResponse.from(deviceAsset);
    }
}
