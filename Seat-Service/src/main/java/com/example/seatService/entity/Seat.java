package com.example.seatService.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "uk_seat_flight_number",
                columnNames = {"flightId", "seatNumber"}
        )
)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private Long flightId;
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatClass seatClass;

    private int rowNum;
    private String columnLetter;

    private boolean isWindow;
    private boolean isAisle;
    private boolean hasExtraLegroom;

    private double priceMultiplier;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    private String lockedByUserId;
    private LocalDateTime lockedAt;
    private LocalDateTime lockExpiresAt;

    @Version
    private Long version;

    public Seat() {}

    public Seat(Long seatId, Long flightId, String seatNumber, SeatClass seatClass,
                int rowNumber, String columnLetter,
                boolean isWindow, boolean isAisle, boolean hasExtraLegroom,
                double priceMultiplier, SeatStatus status) {
        this.seatId = seatId;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.rowNum = rowNumber;
        this.columnLetter = columnLetter;
        this.isWindow = isWindow;
        this.isAisle = isAisle;
        this.hasExtraLegroom = hasExtraLegroom;
        this.priceMultiplier = priceMultiplier;
        this.status = status;
    }

    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public SeatClass getSeatClass() { return seatClass; }
    public void setSeatClass(SeatClass seatClass) { this.seatClass = seatClass; }

    public int getRowNumber() { return rowNum; }
    public void setRowNumber(int rowNumber) { this.rowNum = rowNumber; }

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

    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }

    public String getLockedByUserId() { return lockedByUserId; }
    public void setLockedByUserId(String lockedByUserId) { this.lockedByUserId = lockedByUserId; }

    public LocalDateTime getLockedAt() { return lockedAt; }
    public void setLockedAt(LocalDateTime lockedAt) { this.lockedAt = lockedAt; }

    public LocalDateTime getLockExpiresAt() { return lockExpiresAt; }
    public void setLockExpiresAt(LocalDateTime lockExpiresAt) { this.lockExpiresAt = lockExpiresAt; }

    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
}
