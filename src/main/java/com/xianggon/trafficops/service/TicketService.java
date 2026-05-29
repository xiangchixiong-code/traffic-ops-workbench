package com.xianggon.trafficops.service;

import com.xianggon.trafficops.common.ResourceNotFoundException;
import com.xianggon.trafficops.domain.DeviceAsset;
import com.xianggon.trafficops.domain.FaultTicket;
import com.xianggon.trafficops.domain.TicketStatus;
import com.xianggon.trafficops.dto.TicketCreateRequest;
import com.xianggon.trafficops.dto.TicketResponse;
import com.xianggon.trafficops.dto.TicketStatusUpdateRequest;
import com.xianggon.trafficops.repository.DeviceAssetRepository;
import com.xianggon.trafficops.repository.FaultTicketRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {

    private final FaultTicketRepository faultTicketRepository;
    private final DeviceAssetRepository deviceAssetRepository;
    private final OperationLogService operationLogService;

    public TicketService(FaultTicketRepository faultTicketRepository,
            DeviceAssetRepository deviceAssetRepository,
            OperationLogService operationLogService) {
        this.faultTicketRepository = faultTicketRepository;
        this.deviceAssetRepository = deviceAssetRepository;
        this.operationLogService = operationLogService;
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> search(TicketStatus status) {
        return faultTicketRepository.search(status).stream()
                .map(TicketResponse::from)
                .toList();
    }

    @Transactional
    public TicketResponse create(TicketCreateRequest request) {
        DeviceAsset deviceAsset = deviceAssetRepository.findById(request.deviceId())
                .orElseThrow(() -> new ResourceNotFoundException("device not found: " + request.deviceId()));

        FaultTicket ticket = new FaultTicket(
                deviceAsset,
                request.title(),
                request.description(),
                request.priority(),
                request.reporter());

        FaultTicket saved = faultTicketRepository.save(ticket);
        operationLogService.save(request.reporter(), "ticket", saved.getId(), "CREATE",
                "ticket created for device " + deviceAsset.getAssetCode());
        return TicketResponse.from(saved);
    }

    @Transactional
    public TicketResponse updateStatus(Long id, TicketStatusUpdateRequest request) {
        FaultTicket ticket = faultTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ticket not found: " + id));
        ticket.updateStatus(request.status());
        operationLogService.save(request.operator(), "ticket", id, "STATUS_UPDATE",
                "ticket moved to " + request.status());
        return TicketResponse.from(ticket);
    }
}
