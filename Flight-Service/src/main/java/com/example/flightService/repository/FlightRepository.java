package com.example.flightService.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.example.flightService.entity.Flight;
import com.example.flightService.entity.FlightStatus;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    List<Flight> findByAirlineId(Long airlineId);

    List<Flight> findByStatus(FlightStatus status);

    @Query("SELECT f FROM Flight f WHERE f.originAirportCode = :origin " +
           "AND f.destinationAirportCode = :dest " +
           "AND f.departureTime BETWEEN :start AND :end")
    List<Flight> searchFlights(
            String origin,
            String dest,
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("SELECT f FROM Flight f WHERE f.originAirportCode = :origin " +
           "AND f.destinationAirportCode = :dest " +
           "AND f.availableSeats > 0 " +
           "AND f.departureTime BETWEEN :start AND :end")
    List<Flight> findAvailableFlights(
            String origin,
            String dest,
            LocalDateTime start,
            LocalDateTime end
    );

    long countByAirlineId(Long airlineId);
}