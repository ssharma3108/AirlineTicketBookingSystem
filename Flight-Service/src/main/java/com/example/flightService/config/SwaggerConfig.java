package com.example.flightService.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI flightOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Flight Service API")
                        .version("1.0")
                        .description("APIs for flight management")
                        .contact(new Contact().name("SkyBooker")));
    }

    @Bean
    public GroupedOpenApi flightGroup() {
        return GroupedOpenApi.builder()
                .group("flight-service")
                .packagesToScan("com.example.flightService.controller")
                .build();
    }
}