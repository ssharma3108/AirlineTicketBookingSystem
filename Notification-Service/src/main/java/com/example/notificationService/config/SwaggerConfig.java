package com.example.notificationService.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI notificationOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Notification Service API")
                        .version("1.0")
                        .description("APIs for email and notification handling")
                        .contact(new Contact().name("SkyBooker")));
    }

    @Bean
    public GroupedOpenApi notificationGroup() {
        return GroupedOpenApi.builder()
                .group("notification-service")
                .packagesToScan("com.example.notificationService.controller")
                .build();
    }
}