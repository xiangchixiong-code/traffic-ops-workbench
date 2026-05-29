package com.xianggon.trafficops.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
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
                        .description("面向高速路段设备台账、巡检记录、故障工单和运维统计的 Spring Boot 示例项目")
                        .contact(new Contact()
                                .name("xiangchixiong-code")
                                .url("https://github.com/xiangchixiong-code")))
                .servers(List.of(new Server()
                        .url("http://localhost:8080")
                        .description("本地开发环境")));
    }
}
