package com.example.flightService.service;

import com.example.flightService.dto.FlightDTO;
import com.example.flightService.entity.FlightStatus;

import java.time.LocalDate;
import java.util.*;


public interface FlightService {


    FlightDTO addFlight(FlightDTO flightDTO);

    // ✅ Read
    FlightDTO getFlightById(Long id);

    FlightDTO getFlightByNumber(String flightNumber);

    List<FlightDTO> getAllFlights();

    List<FlightDTO> searchFlights(String origin, String dest, LocalDate date);

    Map<String, List<FlightDTO>> searchRoundTrip(
            String origin,
            String dest,
            LocalDate departDate,
            LocalDate returnDate
    );

    List<FlightDTO> getFlightsByAirline(Long airlineId);

    FlightDTO updateFlight(Long id, FlightDTO flightDTO);

    FlightDTO updateStatus(Long id, FlightStatus status);

    void decrementSeats(Long flightId, int seats);

    void incrementSeats(Long flightId, int seats);

    void deleteFlight(Long id);
}