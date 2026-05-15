package com.example.bookingService.dto;

public class FareSummaryDTO {

    private Double baseFare;
    private Double taxes;
    private Double totalFare;

    public FareSummaryDTO() {
    }

    public FareSummaryDTO(Double baseFare, Double taxes, Double totalFare) {
        this.baseFare = baseFare;
        this.taxes = taxes;
        this.totalFare = totalFare;
    }

    public Double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(Double baseFare) {
        this.baseFare = baseFare;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }
}