package com.example.bookingService.dto;

import java.util.UUID;

public class BookingResponseDTO {

    private UUID bookingId;
    private String pnrCode;
    private String status;
    private String refundStatus;   // NON_REFUNDED / REQUESTED / REFUNDED / REJECTED
    private Double totalFare;
    private String seatNumber;
    private Long userId;
    private Long flightId;
    private java.time.LocalDateTime bookedAt;


    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public String getPnrCode() {
        return pnrCode;
    }

    public void setPnrCode(String pnrCode) {
        this.pnrCode = pnrCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public java.time.LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(java.time.LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

}
