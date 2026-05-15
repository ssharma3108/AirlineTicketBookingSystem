package com.example.airlineService.repository;

import com.example.airlineService.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    Optional<Airline> findByIataCode(String iataCode);

    List<Airline> findByIsActive(boolean isActive);
}