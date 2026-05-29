package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.RoadSection;

public record RoadSectionResponse(
        Long id,
        String name,
        String code,
        String region,
        Double mileage) {

    public static RoadSectionResponse from(RoadSection roadSection) {
        return new RoadSectionResponse(
                roadSection.getId(),
                roadSection.getName(),
                roadSection.getCode(),
                roadSection.getRegion(),
                roadSection.getMileage());
    }
}
