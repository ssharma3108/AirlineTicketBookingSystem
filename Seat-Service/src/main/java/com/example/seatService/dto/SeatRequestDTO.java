package com.example.seatService.dto;

import com.example.seatService.entity.SeatClass;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class SeatRequestDTO {

	@NotNull(message = "FlightId is required")
    private Long flightId;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @NotNull(message = "Seat class is required")
    private SeatClass seatClass;

    @Min(value = 1, message = "Row number must be greater than 0")
    private int rowNumber;

    @NotBlank(message = "Column letter is required")
    private String columnLetter;

    private boolean isWindow;
    private boolean isAisle;
    private boolean hasExtraLegroom;

    @PositiveOrZero(message = "Price multiplier must be 0 or more")
    private double priceMultiplier;

    public SeatRequestDTO() {}

    // getters & setters

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public SeatClass getSeatClass() { return seatClass; }
    public void setSeatClass(SeatClass seatClass) { this.seatClass = seatClass; }

    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }

    public String getColumnLetter() { return columnLetter; }
    public void setColumnLetter(String columnLetter) { this.columnLetter = columnLetter; }

    public boolean isWindow() { return isWindow; }
    public void setWindow(boolean window) { isWindow = window; }

    public boolean isAisle() { return isAisle; }
    public void setAisle(boolean aisle) { isAisle = aisle; }

    public boolean isHasExtraLegroom() { return hasExtraLegroom; }
    public void setHasExtraLegroom(boolean hasExtraLegroom) { this.hasExtraLegroom = hasExtraLegroom; }

    public double getPriceMultiplier() { return priceMultiplier; }
    public void setPriceMultiplier(double priceMultiplier) { this.priceMultiplier = priceMultiplier; }
}