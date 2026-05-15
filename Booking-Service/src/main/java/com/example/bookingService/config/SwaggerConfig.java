package com.example.bookingService.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bookingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Booking Service API")
                        .version("1.0")
                        .description("APIs for booking management")
                        .contact(new Contact().name("SkyBooker")));
    }

    @Bean
    public GroupedOpenApi bookingGroup() {
        return GroupedOpenApi.builder()
                .group("booking-service")
                .packagesToScan("com.example.bookingService.controller")
                .build();
    }
}