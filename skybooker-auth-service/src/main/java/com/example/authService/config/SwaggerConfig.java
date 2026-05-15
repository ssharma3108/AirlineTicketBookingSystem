package com.example.authService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI authOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Auth Service API")
                        .version("1.0")
                        .description("APIs for authentication and authorization")
                        .contact(new Contact().name("SkyBooker")));
    }

    @Bean
    public org.springdoc.core.models.GroupedOpenApi authGroup() {
        return org.springdoc.core.models.GroupedOpenApi.builder()
                .group("auth-service")
                .packagesToScan("com.example.authService.controller")
                .build();
    }
}