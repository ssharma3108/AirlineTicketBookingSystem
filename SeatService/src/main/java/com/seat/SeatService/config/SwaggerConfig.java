package com.seat.SeatService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Seat Service API")
                        .version("1.0")
                        .description("Seat Management APIs for SkyBooker Airline Booking System")
                        .contact(new Contact()
                                .name("SkyBooker Team")
                                .email("support@skybooker.com")));
    }
}