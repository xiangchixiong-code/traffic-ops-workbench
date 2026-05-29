package com.xianggon.trafficops.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI trafficOpsOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("交通信息化工单与设备台账系统 API")
                        .version("v1.0")
                        .description("""
                                面向运营商政企/DICT行业信息化场景的后端接口服务。
                                业务范围包括高速路段设备台账、巡检记录、故障工单、操作日志、统计看板和 Excel 台账导入导出。
                                """))
                .servers(List.of(new Server()
                        .url("http://localhost:8080")
                        .description("本地开发环境")));
    }
}
