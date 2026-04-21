// FlightController.java
package com.flight.FlightService.controller;

import com.flight.FlightService.dto.SeatUpdateRequest;
import com.flight.FlightService.dto.StatusUpdateRequest;
import com.flight.FlightService.entity.Flight;
import com.flight.FlightService.entity.FlightStatus;
import com.flight.FlightService.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
@CrossOrigin("*")
public class FlightController {

    @Autowired
    private FlightService flightService;

    // Add Flight
    @PostMapping("/add")
    public Flight addFlight(@Valid @RequestBody Flight flight) {
        return flightService.addFlight(flight);
    }

    // Get All Flights
    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    // Get Flight By ID
    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable Integer id) {
        return flightService.getFlightById(id);
    }

    // Get By Flight Number
    @GetMapping("/number/{flightNumber}")
    public Flight getByFlightNumber(@PathVariable String flightNumber) {
        return flightService.getFlightByFlightNumber(flightNumber);
    }

    // Get Flights By Airline Id
    @GetMapping("/airline/{airlineId}")
    public List<Flight> getByAirlineId(@PathVariable Integer airlineId) {
        return flightService.getFlightsByAirlineId(airlineId);
    }

    // Get Flights By Status
    @GetMapping("/status/{status}")
    public List<Flight> getByStatus(@PathVariable FlightStatus status) {
        return flightService.getFlightsByStatus(status);
    }

    // Search One Way Flights
    @GetMapping("/search")
    public List<Flight> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam String departureTime
    ) {
        return flightService.searchFlights(
                origin,
                destination,
                LocalDateTime.parse(departureTime)
        );
    }

    // Available Flights
    @GetMapping("/available")
    public List<Flight> availableFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam Integer seats
    ) {
        return flightService.getAvailableFlights(origin, destination, seats);
    }

    // Count By Airline
    @GetMapping("/count/{airlineId}")
    public Long countFlights(@PathVariable Integer airlineId) {
        return flightService.countFlightsByAirlineId(airlineId);
    }

    // Update Full Flight
    @PutMapping("/{id}")
    public Flight updateFlight(
            @PathVariable Integer id,
            @RequestBody Flight flight
    ) {
        return flightService.updateFlight(id, flight);
    }

    // Update Flight Status
    @PatchMapping("/{id}/status")
    public Flight updateStatus(
            @PathVariable Integer id,
            @RequestBody StatusUpdateRequest request
    ) {
        return flightService.updateFlightStatus(id, request.getStatus());
    }

    // Reserve Seats
    @PatchMapping("/{id}/reserve")
    public Flight reserveSeats(
            @PathVariable Integer id,
            @RequestBody SeatUpdateRequest request
    ) {
        return flightService.reserveSeats(id, request.getSeats());
    }

    // Release Seats
    @PatchMapping("/{id}/release")
    public Flight releaseSeats(
            @PathVariable Integer id,
            @RequestBody SeatUpdateRequest request
    ) {
        return flightService.releaseSeats(id, request.getSeats());
    }

    // Delete Flight
    @DeleteMapping("/{id}")
    public String deleteFlight(@PathVariable Integer id) {
        flightService.deleteFlight(id);
        return "Flight deleted successfully";
    }
}