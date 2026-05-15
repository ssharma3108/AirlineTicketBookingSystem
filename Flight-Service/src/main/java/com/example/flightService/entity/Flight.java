package com.example.flightService.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;

    private String flightNumber;
    private Long airlineId;

    private String originAirportCode;
    private String destinationAirportCode;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    private String aircraftType;

    private Integer totalSeats;
    private Integer availableSeats;

    private Double basePrice;

    //  Default Constructor
    public Flight() {}

    // Parameterized Constructor
    public Flight(Long flightId, String flightNumber, Long airlineId,
                  String originAirportCode, String destinationAirportCode,
                  LocalDateTime departureTime, LocalDateTime arrivalTime,
                  Integer durationMinutes, FlightStatus status,
                  String aircraftType, Integer totalSeats,
                  Integer availableSeats, Double basePrice) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.airlineId = airlineId;
        this.originAirportCode = originAirportCode;
        this.destinationAirportCode = destinationAirportCode;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.durationMinutes = durationMinutes;
        this.status = status;
        this.aircraftType = aircraftType;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.basePrice = basePrice;
    }

    // 🔹 Getters & Setters

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public Long getAirlineId() { return airlineId; }
    public void setAirlineId(Long airlineId) { this.airlineId = airlineId; }

    public String getOriginAirportCode() { return originAirportCode; }
    public void setOriginAirportCode(String originAirportCode) { this.originAirportCode = originAirportCode; }

    public String getDestinationAirportCode() { return destinationAirportCode; }
    public void setDestinationAirportCode(String destinationAirportCode) { this.destinationAirportCode = destinationAirportCode; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public FlightStatus getStatus() { return status; }
    public void setStatus(FlightStatus status) { this.status = status; }

    public String getAircraftType() { return aircraftType; }
    public void setAircraftType(String aircraftType) { this.aircraftType = aircraftType; }

    public Integer getTotalSeats() { return totalSeats; }
    public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }

    public Integer getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }

    public Double getBasePrice() { return basePrice; }
    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }
}
