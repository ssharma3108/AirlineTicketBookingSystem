package com.admin.Admin_Service.service.impl;

import com.admin.Admin_Service.client.*;
import com.admin.Admin_Service.dto.*;
import com.admin.Admin_Service.entity.AuditLog;
import com.admin.Admin_Service.repository.AuditLogRepository;
import com.admin.Admin_Service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {



    private  UserClient userClient;
    private  FlightClient flightClient;
    private  BookingClient bookingClient;
    private  PaymentClient paymentClient;
    private  AuditLogRepository auditLogRepository;

    private void log(String action, String target) {
        auditLogRepository.save(
                AuditLog.builder()
                        .action(action)
                        .target(target)
                        .performedBy("ADMIN")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public DashboardDTO getDashboardData() {

        Long totalUsers = userClient.getUserCount();
        Long totalBookings = bookingClient.getBookingCount();
        Double totalRevenue = paymentClient.getTotalRevenue();

        return DashboardDTO.builder()
                .totalUsers(totalUsers)
                .totalBookings(totalBookings)
                .totalRevenue(totalRevenue)
                .build();
    }

    @Override
    public Object getAllUsers() {
        return userClient.getAllUsers();
    }

    @Override
    public String suspendUser(Long userId) {
        log("SUSPEND_USER", String.valueOf(userId));
        return userClient.suspendUser(userId);
    }

    @Override
    public String deleteUser(Long userId) {
        log("DELETE_USER", String.valueOf(userId));
        return userClient.deleteUser(userId);
    }

    @Override
    public Object getAllAirlines() {
        return flightClient.getAllAirlines();
    }

    @Override
    public Object createAirline(AirlineDTO dto) {
        log("CREATE_AIRLINE", dto.getName());
        return flightClient.createAirline(dto);
    }

    @Override
    public String deactivateAirline(Long id) {
        log("DEACTIVATE_AIRLINE", String.valueOf(id));
        return flightClient.deactivateAirline(id);
    }

    @Override
    public Object getAllAirports() {
        return flightClient.getAllAirports();
    }

    @Override
    public Object createAirport(AirportDTO dto) {
        log("CREATE_AIRPORT", dto.getName());
        return flightClient.createAirport(dto);
    }

    @Override
    public Object updateAirport(Long id, AirportDTO dto) {
        log("UPDATE_AIRPORT", String.valueOf(id));
        return flightClient.updateAirport(id, dto);
    }

    @Override
    public Object getAllBookings() {
        return bookingClient.getAllBookings();
    }

    @Override
    public Object getAllPayments() {
        return paymentClient.getAllPayments();
    }

    @Override
    public AnalyticsDTO getAnalytics() {
        return AnalyticsDTO.builder()
                .totalUsers((long) userClient.getAllUsers().size())
                .totalBookings((long) bookingClient.getAllBookings().size())
                .totalRevenue(paymentClient.getTotalRevenue())
                .build();
    }

    @Override
    public Double getRevenueReport() {
        return paymentClient.getTotalRevenue();
    }

    @Override
    public String sendNotification(String message) {
        log("SEND_NOTIFICATION", message);
        return "Notification sent: " + message;
    }

    @Override
    public Object getAuditLogs() {
        return auditLogRepository.findAll();
    }
}