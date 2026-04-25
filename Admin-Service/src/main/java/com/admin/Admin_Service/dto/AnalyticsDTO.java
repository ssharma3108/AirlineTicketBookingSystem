package com.admin.Admin_Service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalyticsDTO {
    private Long totalUsers;
    private Long totalBookings;
    private Double totalRevenue;
}