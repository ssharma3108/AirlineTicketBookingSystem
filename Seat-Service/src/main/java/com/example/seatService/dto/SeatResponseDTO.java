package com.example.seatService.dto;

import com.example.seatService.entity.SeatClass;
import com.example.seatService.entity.SeatStatus;

public class SeatResponseDTO {

    private Long seatId;
    private Long flightId;
    private String seatNumber;
    private SeatClass seatClass;
    private int rowNumber;
    private String columnLetter;
    private SeatStatus status;

    public SeatResponseDTO() {}

    public SeatResponseDTO(Long seatId, Long flightId, String seatNumber,
                           SeatClass seatClass, int rowNumber,
                           String columnLetter, SeatStatus status) {
        this.seatId = seatId;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.rowNumber = rowNumber;
        this.columnLetter = columnLetter;
        this.status = status;
    }

    // getters & setters

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

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

    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }
}