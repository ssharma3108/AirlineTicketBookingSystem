package com.airline.Airline_Service.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI airlineServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Airline Service API")
                        .description("Airline & Airport Management APIs for SkyBooker")
                        .version("1.0"));
    }
}