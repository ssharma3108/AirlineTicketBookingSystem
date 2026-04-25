package com.admin.Admin_Service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {

    @GetMapping("/api/payments")
    List<Object> getAllPayments();

    @GetMapping("/api/payments/revenue")
    Double getTotalRevenue();
}