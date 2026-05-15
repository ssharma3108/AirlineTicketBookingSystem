package com.example.passengerService.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI passengerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Passenger Service API")
                        .version("1.0")
                        .description("APIs for passenger management")
                        .contact(new Contact().name("SkyBooker")));
    }

    @Bean
    public GroupedOpenApi passengerGroup() {
        return GroupedOpenApi.builder()
                .group("passenger-service")
                .packagesToScan("com.example.passengerService.controller")
                .build();
    }
}
