package com.example.flightService.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.flightService.dto.FlightDTO;
import com.example.flightService.entity.FlightStatus;
import com.example.flightService.service.FlightService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    // Add Flight
    @PostMapping
    public FlightDTO addFlight(@Valid @RequestBody FlightDTO dto) {
        return flightService.addFlight(dto);
    }

    // Get by ID
    @GetMapping("/{id}")
    public FlightDTO getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    // Get by Flight Number
    @GetMapping("/number/{flightNumber}")
    public FlightDTO getFlightByNumber(@PathVariable String flightNumber) {
        return flightService.getFlightByNumber(flightNumber);
    }

    // Get all flights
    @GetMapping
    public List<FlightDTO> getAllFlights() {
        return flightService.getAllFlights();
    }

    // Search Flights (one-way)
    @GetMapping("/search")
    public List<FlightDTO> searchFlights(
            @RequestParam String origin,
            @RequestParam String dest,
            @RequestParam String date   // yyyy-MM-dd
    ) {
        LocalDate localDate = LocalDate.parse(date);
        return flightService.searchFlights(origin, dest, localDate);
    }

    // Round Trip Search
    @GetMapping("/search/roundtrip")
    public Map<String, List<FlightDTO>> searchRoundTrip(
            @RequestParam String origin,
            @RequestParam String dest,
            @RequestParam String departDate,
            @RequestParam String returnDate
    ) {
        return flightService.searchRoundTrip(
                origin,
                dest,
                LocalDate.parse(departDate),
                LocalDate.parse(returnDate)
        );
    }

    // Update Flight
    @PutMapping("/{id}")
    public FlightDTO updateFlight(
            @PathVariable Long id,
            @RequestBody FlightDTO dto
    ) {
        return flightService.updateFlight(id, dto);
    }

    // Update Status
    @PatchMapping("/{id}/status")
    public FlightDTO updateStatus(
            @PathVariable Long id,
            @RequestParam FlightStatus status
    ) {
        return flightService.updateStatus(id, status);
    }

    // Decrement Seats
    @PutMapping("/{id}/decrement")
    public String decrementSeats(@PathVariable Long id, @RequestParam int count) {
        flightService.decrementSeats(id, count);
        return "Seats decremented successfully";
    }

    // Increment Seats
    @PutMapping("/{id}/increment")
    public String incrementSeats(@PathVariable Long id, @RequestParam int count) {
        flightService.incrementSeats(id, count);
        return "Seats incremented successfully";
    }


    // Delete Flight
    @DeleteMapping("/{id}")
    public String deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return "Flight deleted successfully";
    }

    // Get Flights by Airline
    @GetMapping("/airline/{airlineId}")
    public List<FlightDTO> getFlightsByAirline(@PathVariable Long airlineId) {
        return flightService.getFlightsByAirline(airlineId);
    }
}