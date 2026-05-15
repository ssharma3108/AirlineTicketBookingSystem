package com.example.airlineService.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI airlineOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Airline Service API")
                        .version("1.0")
                        .description("APIs for airline management")
                        .contact(new Contact().name("SkyBooker")));
    }

    @Bean
    public GroupedOpenApi airlineGroup() {
        return GroupedOpenApi.builder()
                .group("airline-service")
                .packagesToScan("com.example.airlineService.controller")
                .build();
    }
}