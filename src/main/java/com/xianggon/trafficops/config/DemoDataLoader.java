package com.xianggon.trafficops.config;

import com.xianggon.trafficops.domain.DeviceAsset;
import com.xianggon.trafficops.domain.DeviceStatus;
import com.xianggon.trafficops.domain.FaultTicket;
import com.xianggon.trafficops.domain.InspectionRecord;
import com.xianggon.trafficops.domain.RoadSection;
import com.xianggon.trafficops.domain.TicketPriority;
import com.xianggon.trafficops.domain.TicketStatus;
import com.xianggon.trafficops.repository.DeviceAssetRepository;
import com.xianggon.trafficops.repository.FaultTicketRepository;
import com.xianggon.trafficops.repository.InspectionRecordRepository;
import com.xianggon.trafficops.repository.RoadSectionRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoDataLoader {

    @Bean
    CommandLineRunner loadDemoData(RoadSectionRepository roadSectionRepository,
            DeviceAssetRepository deviceAssetRepository,
            FaultTicketRepository faultTicketRepository,
            InspectionRecordRepository inspectionRecordRepository) {
        return args -> {
            if (roadSectionRepository.count() > 0) {
                return;
            }

            RoadSection yinchuanNorth = new RoadSection("银川北收费站至永宁段", "NX-G6-YC-NY", "银川", 38.5);
            RoadSection shizuishanWest = new RoadSection("石嘴山西互通段", "NX-G6-SZS-XH", "石嘴山", 24.3);
            RoadSection zhongweiSouth = new RoadSection("中卫南枢纽段", "NX-G70-ZW-SN", "中卫", 19.8);
            roadSectionRepository.saveAll(List.of(yinchuanNorth, shizuishanWest, zhongweiSouth));

            DeviceAsset camera = new DeviceAsset("收费站视频监控摄像头", "CAM-YC-001", "Camera",
                    DeviceStatus.ONLINE, yinchuanNorth, "银川北收费站入口", LocalDate.of(2024, 8, 12));
            DeviceAsset sensor = new DeviceAsset("路面温湿度传感器", "SEN-SZS-002", "Sensor",
                    DeviceStatus.WARNING, shizuishanWest, "石嘴山西主线桩号K132", LocalDate.of(2024, 10, 3));
            DeviceAsset screen = new DeviceAsset("信息发布屏", "LED-ZW-003", "DisplayBoard",
                    DeviceStatus.MAINTENANCE, zhongweiSouth, "中卫南服务区", LocalDate.of(2023, 5, 19));
            deviceAssetRepository.saveAll(List.of(camera, sensor, screen));

            faultTicketRepository.save(new FaultTicket(
                    sensor,
                    "传感器数据波动",
                    "夜间采集值波动较大，需要复核采集链路和供电情况。",
                    TicketPriority.HIGH,
                    "运维值班员"));
            faultTicketRepository.save(new FaultTicket(
                    screen,
                    "信息屏离线",
                    "服务区信息发布屏无法刷新，需要现场检查网络和电源。",
                    TicketPriority.URGENT,
                    "调度中心"));

            inspectionRecordRepository.save(new InspectionRecord(
                    camera,
                    "曹博轩",
                    LocalDateTime.now().minusDays(2),
                    "PASS",
                    "镜头画面清晰，角度正常。"));
            inspectionRecordRepository.save(new InspectionRecord(
                    sensor,
                    "曹博轩",
                    LocalDateTime.now().minusDays(1),
                    "WARN",
                    "数据波动偏大，建议复核连接和环境因素。"));
        };
    }
}
