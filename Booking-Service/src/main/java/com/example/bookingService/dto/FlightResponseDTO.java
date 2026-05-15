package com.example.bookingService.dto;

public class FlightResponseDTO {

    private Long flightId;
    private Double baseFare;
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	public Double getBaseFare() {
		return baseFare;
	}
	public void setBaseFare(Double baseFare) {
		this.baseFare = baseFare;
	}

    // getters setters
}