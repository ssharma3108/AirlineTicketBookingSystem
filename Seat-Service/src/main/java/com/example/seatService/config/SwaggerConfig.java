package com.example.seatService.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI seatOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Seat Service API")
                        .version("1.0")
                        .description("APIs for seat availability and seat booking")
                        .contact(new Contact().name("SkyBooker")));
    }

    @Bean
    public GroupedOpenApi seatGroup() {
        return GroupedOpenApi.builder()
                .group("seat-service")
                .packagesToScan("com.example.seatService.controller")
                .build();
    }
}
