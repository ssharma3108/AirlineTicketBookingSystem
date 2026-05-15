package com.example.paymentService.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI paymentOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Payment Service API")
                        .version("1.0")
                        .description("APIs for payment and transaction handling")
                        .contact(new Contact().name("SkyBooker")));
    }

    @Bean
    public GroupedOpenApi paymentGroup() {
        return GroupedOpenApi.builder()
                .group("payment-service")
                .packagesToScan("com.example.paymentService.controller")
                .build();
    }
}
