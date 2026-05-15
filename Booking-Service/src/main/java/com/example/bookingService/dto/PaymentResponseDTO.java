package com.example.bookingService.dto;

import java.util.UUID;

public class PaymentResponseDTO {

    private UUID paymentId;
    private UUID bookingId;  
    private String status;
	public UUID getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(UUID paymentId) {
		this.paymentId = paymentId;
	}
	public UUID getBookingId() {
		return bookingId;
	}
	public void setBookingId(UUID bookingId) {
		this.bookingId = bookingId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

    // getters setters
}
