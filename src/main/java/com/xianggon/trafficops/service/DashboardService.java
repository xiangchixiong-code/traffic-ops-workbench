package com.xianggon.trafficops.service;

import com.xianggon.trafficops.domain.DeviceStatus;
import com.xianggon.trafficops.domain.TicketStatus;
import com.xianggon.trafficops.dto.DashboardResponse;
import com.xianggon.trafficops.repository.DeviceAssetRepository;
import com.xianggon.trafficops.repository.FaultTicketRepository;
import com.xianggon.trafficops.repository.RoadSectionRepository;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

    private final RoadSectionRepository roadSectionRepository;
    private final DeviceAssetRepository deviceAssetRepository;
    private final FaultTicketRepository faultTicketRepository;

    public DashboardService(RoadSectionRepository roadSectionRepository,
            DeviceAssetRepository deviceAssetRepository,
            FaultTicketRepository faultTicketRepository) {
        this.roadSectionRepository = roadSectionRepository;
        this.deviceAssetRepository = deviceAssetRepository;
        this.faultTicketRepository = faultTicketRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResponse summary() {
        Map<String, Long> devicesByStatus = new LinkedHashMap<>();
        for (DeviceStatus status : DeviceStatus.values()) {
            devicesByStatus.put(status.name(), deviceAssetRepository.countByStatus(status));
        }

        Map<String, Long> ticketsByStatus = new LinkedHashMap<>();
        for (TicketStatus status : TicketStatus.values()) {
            ticketsByStatus.put(status.name(), faultTicketRepository.countByStatus(status));
        }

        return new DashboardResponse(
                roadSectionRepository.count(),
                deviceAssetRepository.count(),
                faultTicketRepository.count(),
                devicesByStatus,
                ticketsByStatus);
    }
}
