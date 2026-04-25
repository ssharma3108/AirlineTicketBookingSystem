package com.admin.Admin_Service.service;

import com.admin.Admin_Service.dto.*;


public interface AdminService {

    DashboardDTO getDashboardData();

    Object getAllUsers();
    String suspendUser(Long userId);
    String deleteUser(Long userId);

    Object getAllAirlines();
    Object createAirline(AirlineDTO dto);
    String deactivateAirline(Long id);

    Object getAllAirports();
    Object createAirport(AirportDTO dto);
    Object updateAirport(Long id, AirportDTO dto);

    Object getAllBookings();
    Object getAllPayments();

    AnalyticsDTO getAnalytics();
    Double getRevenueReport();

    String sendNotification(String message);

    Object getAuditLogs();
}