package com.passenger.PassengerService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI passengerServiceAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Passenger Service API")
                        .description("Handles passenger management, seat assignment, and ticket generation")
                        .version("1.0"));
    }
}
