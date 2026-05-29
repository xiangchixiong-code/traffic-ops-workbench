package com.xianggon.trafficops.service;

import com.xianggon.trafficops.common.ResourceNotFoundException;
import com.xianggon.trafficops.domain.DeviceAsset;
import com.xianggon.trafficops.domain.InspectionRecord;
import com.xianggon.trafficops.dto.InspectionCreateRequest;
import com.xianggon.trafficops.dto.InspectionResponse;
import com.xianggon.trafficops.repository.DeviceAssetRepository;
import com.xianggon.trafficops.repository.InspectionRecordRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InspectionService {

    private final InspectionRecordRepository inspectionRecordRepository;
    private final DeviceAssetRepository deviceAssetRepository;
    private final OperationLogService operationLogService;

    public InspectionService(InspectionRecordRepository inspectionRecordRepository,
            DeviceAssetRepository deviceAssetRepository,
            OperationLogService operationLogService) {
        this.inspectionRecordRepository = inspectionRecordRepository;
        this.deviceAssetRepository = deviceAssetRepository;
        this.operationLogService = operationLogService;
    }

    @Transactional(readOnly = true)
    public List<InspectionResponse> recent() {
        return inspectionRecordRepository.findRecent().stream()
                .map(InspectionResponse::from)
                .toList();
    }

    @Transactional
    public InspectionResponse create(InspectionCreateRequest request) {
        DeviceAsset deviceAsset = deviceAssetRepository.findById(request.deviceId())
                .orElseThrow(() -> new ResourceNotFoundException("device not found: " + request.deviceId()));

        InspectionRecord record = new InspectionRecord(
                deviceAsset,
                request.inspector(),
                request.inspectedAt(),
                request.result(),
                request.notes());

        InspectionRecord saved = inspectionRecordRepository.save(record);
        operationLogService.save(request.inspector(), "inspection", saved.getId(), "CREATE",
                "inspection logged for device " + deviceAsset.getAssetCode());
        return InspectionResponse.from(saved);
    }
}
