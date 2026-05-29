package com.xianggon.trafficops.web;

import com.xianggon.trafficops.common.ApiResponse;
import com.xianggon.trafficops.dto.RoadSectionResponse;
import com.xianggon.trafficops.service.RoadSectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/road-sections")
@Tag(name = "路段基础数据", description = "高速路段、区域和里程基础信息")
public class RoadSectionController {

    private final RoadSectionService roadSectionService;

    public RoadSectionController(RoadSectionService roadSectionService) {
        this.roadSectionService = roadSectionService;
    }

    @GetMapping
    @Operation(summary = "查询路段列表", description = "Excel 导入时可使用这里返回的路段编码")
    public ApiResponse<List<RoadSectionResponse>> list() {
        return ApiResponse.ok(roadSectionService.list());
    }
}
