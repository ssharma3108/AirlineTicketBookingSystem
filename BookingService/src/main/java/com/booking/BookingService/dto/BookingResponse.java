package com.booking.BookingService.dto;

import com.booking.BookingService.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private String bookingId;
    private String pnrCode;
    private BookingStatus status;
    private Double totalFare;
    private String message;
}