package com.flight.FlightService.repository;

import com.flight.FlightService.entity.Flight;
import com.flight.FlightService.entity.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    // Find by flight number
    Optional<Flight> findByFlightNumber(String flightNumber);

    // Find by source + destination + departure date/time
    List<Flight> findByOriginAirportCodeAndDestinationAirportCodeAndDepartureTime(
            String originAirportCode,
            String destinationAirportCode,
            LocalDateTime departureTime
    );

    // Find by airline id
    List<Flight> findByAirlineId(Integer airlineId);

    // Find by status
    List<Flight> findByStatus(FlightStatus status);

    // Find by flight id
    Optional<Flight> findById(Integer id);

    // Find only available flights
    List<Flight> findByOriginAirportCodeAndDestinationAirportCodeAndAvailableSeatsGreaterThan(
            String originAirportCode,
            String destinationAirportCode,
            Integer seats
    );

    // Count flights by airline id
    Long countByAirlineId(Integer airlineId);
}