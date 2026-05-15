package com.example.airlineService.repository;

import com.example.airlineService.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    Optional<Airport> findByIataCode(String iataCode);

    List<Airport> findByCity(String city);

    List<Airport> findByCountry(String country);

    List<Airport> findByCityContainingIgnoreCase(String city);
}