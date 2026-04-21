package com.booking.BookingService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookingOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("SkyBooker Booking Service API")
                        .version("1.0")
                        .description("Booking Microservice APIs"));
    }
}