// FlightService.java
package com.flight.FlightService.service;

import com.flight.FlightService.entity.Flight;
import com.flight.FlightService.entity.FlightStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {

    Flight addFlight(Flight flight);

    Flight getFlightById(Integer id);

    Flight getFlightByFlightNumber(String flightNumber);

    List<Flight> getAllFlights();

    List<Flight> getFlightsByAirlineId(Integer airlineId);

    List<Flight> getFlightsByStatus(FlightStatus status);

    List<Flight> searchFlights(String originAirportCode,
                               String destinationAirportCode,
                               LocalDateTime departureTime);

    List<Flight> getAvailableFlights(String originAirportCode,
                                     String destinationAirportCode,
                                     Integer seats);

    Long countFlightsByAirlineId(Integer airlineId);

    Flight updateFlightStatus(Integer id, FlightStatus status);

    Flight updateFlight(Integer id, Flight flight);

    void deleteFlight(Integer id);

    Flight reserveSeats(Integer id, Integer seats);

    Flight releaseSeats(Integer id, Integer seats);
}