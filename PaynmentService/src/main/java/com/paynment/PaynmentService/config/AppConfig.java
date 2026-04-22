package com.paynment.PaynmentService.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // 🔹 Optional: If you use RestTemplate anywhere
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 🔹 Feign logging (VERY IMPORTANT for debugging)
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}