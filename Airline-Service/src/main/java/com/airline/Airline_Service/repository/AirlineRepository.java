package com.airline.Airline_Service.repository;

import com.airline.Airline_Service.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    Optional<Airline> findByIataCode(String iataCode);

    List<Airline> findByIsActive(Boolean isActive);
}