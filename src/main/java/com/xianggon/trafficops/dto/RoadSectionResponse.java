package com.xianggon.trafficops.dto;

import com.xianggon.trafficops.domain.RoadSection;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "路段基础数据返回数据")
public record RoadSectionResponse(
        @Schema(description = "路段 ID", example = "1")
        Long id,
        @Schema(description = "路段名称", example = "银川北收费站至永宁段")
        String name,
        @Schema(description = "路段编码，Excel 导入时使用", example = "NX-G6-YC-NY")
        String code,
        @Schema(description = "所属区域", example = "银川")
        String region,
        @Schema(description = "里程，单位公里", example = "38.5")
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
