package com.xianggon.trafficops.service;

import com.xianggon.trafficops.dto.RoadSectionResponse;
import com.xianggon.trafficops.repository.RoadSectionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoadSectionService {

    private final RoadSectionRepository roadSectionRepository;

    public RoadSectionService(RoadSectionRepository roadSectionRepository) {
        this.roadSectionRepository = roadSectionRepository;
    }

    @Transactional(readOnly = true)
    public List<RoadSectionResponse> list() {
        return roadSectionRepository.findAll().stream()
                .map(RoadSectionResponse::from)
                .toList();
    }
}
