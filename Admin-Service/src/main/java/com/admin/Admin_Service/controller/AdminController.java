package com.admin.Admin_Service.controller;

import com.admin.Admin_Service.dto.*;
import com.admin.Admin_Service.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> dashboard() {
        return ResponseEntity.ok(adminService.getDashboardData());
    }

    @GetMapping("/users")
    public ResponseEntity<?> users() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PutMapping("/users/{id}/suspend")
    public ResponseEntity<?> suspend(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.suspendUser(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteUser(id));
    }

    @GetMapping("/airlines")
    public ResponseEntity<?> airlines() {
        return ResponseEntity.ok(adminService.getAllAirlines());
    }

    @PostMapping("/airlines")
    public ResponseEntity<?> createAirline(@RequestBody AirlineDTO dto) {
        return ResponseEntity.ok(adminService.createAirline(dto));
    }

    @GetMapping("/airports")
    public ResponseEntity<?> airports() {
        return ResponseEntity.ok(adminService.getAllAirports());
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> bookings() {
        return ResponseEntity.ok(adminService.getAllBookings());
    }

    @GetMapping("/payments")
    public ResponseEntity<?> payments() {
        return ResponseEntity.ok(adminService.getAllPayments());
    }

    @GetMapping("/analytics")
    public ResponseEntity<?> analytics() {
        return ResponseEntity.ok(adminService.getAnalytics());
    }

    @GetMapping("/revenue")
    public ResponseEntity<?> revenue() {
        return ResponseEntity.ok(adminService.getRevenueReport());
    }

    @GetMapping("/audit")
    public ResponseEntity<?> audit() {
        return ResponseEntity.ok(adminService.getAuditLogs());
    }
}