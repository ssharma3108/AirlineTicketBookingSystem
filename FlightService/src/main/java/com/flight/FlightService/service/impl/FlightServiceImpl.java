// FlightServiceImpl.java
package com.flight.FlightService.service.impl;

import com.flight.FlightService.entity.Flight;
import com.flight.FlightService.entity.FlightStatus;
import com.flight.FlightService.repository.FlightRepository;
import com.flight.FlightService.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public Flight addFlight(Flight flight) {
        if (flight.getAvailableSeats() == null) {
            flight.setAvailableSeats(flight.getTotalSeats());
        }
        return flightRepository.save(flight);
    }

    @Override
    public Flight getFlightById(Integer id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    @Override
    public Flight getFlightByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public List<Flight> getFlightsByAirlineId(Integer airlineId) {
        return flightRepository.findByAirlineId(airlineId);
    }

    @Override
    public List<Flight> getFlightsByStatus(FlightStatus status) {
        return flightRepository.findByStatus(status);
    }

    @Override
    public List<Flight> searchFlights(String originAirportCode,
                                      String destinationAirportCode,
                                      LocalDateTime departureTime) {

        return flightRepository
                .findByOriginAirportCodeAndDestinationAirportCodeAndDepartureTime(
                        originAirportCode,
                        destinationAirportCode,
                        departureTime
                );
    }

    @Override
    public List<Flight> getAvailableFlights(String originAirportCode,
                                            String destinationAirportCode,
                                            Integer seats) {

        return flightRepository
                .findByOriginAirportCodeAndDestinationAirportCodeAndAvailableSeatsGreaterThan(
                        originAirportCode,
                        destinationAirportCode,
                        seats
                );
    }

    @Override
    public Long countFlightsByAirlineId(Integer airlineId) {
        return flightRepository.countByAirlineId(airlineId);
    }

    @Override
    public Flight updateFlightStatus(Integer id, FlightStatus status) {
        Flight flight = getFlightById(id);
        flight.setStatus(status);
        return flightRepository.save(flight);
    }

    @Override
    public Flight updateFlight(Integer id, Flight flight) {

        Flight existing = getFlightById(id);

        existing.setFlightNumber(flight.getFlightNumber());
        existing.setAirlineId(flight.getAirlineId());
        existing.setOriginAirportCode(flight.getOriginAirportCode());
        existing.setDestinationAirportCode(flight.getDestinationAirportCode());
        existing.setDepartureTime(flight.getDepartureTime());
        existing.setArrivalTime(flight.getArrivalTime());
        existing.setDurationMinutes(flight.getDurationMinutes());
        existing.setAircraftType(flight.getAircraftType());
        existing.setTotalSeats(flight.getTotalSeats());
        existing.setAvailableSeats(flight.getAvailableSeats());
        existing.setStatus(flight.getStatus());

        return flightRepository.save(existing);
    }

    @Override
    public void deleteFlight(Integer id) {
        Flight flight = getFlightById(id);
        flightRepository.delete(flight);
    }

    @Override
    public Flight reserveSeats(Integer id, Integer seats) {

        Flight flight = getFlightById(id);

        if (flight.getAvailableSeats() < seats) {
            throw new RuntimeException("Seats not available");
        }

        flight.setAvailableSeats(
                flight.getAvailableSeats() - seats
        );

        return flightRepository.save(flight);
    }

    @Override
    public Flight releaseSeats(Integer id, Integer seats) {

        Flight flight = getFlightById(id);

        int updatedSeats = flight.getAvailableSeats() + seats;

        if (updatedSeats > flight.getTotalSeats()) {
            updatedSeats = flight.getTotalSeats();
        }

        flight.setAvailableSeats(updatedSeats);

        return flightRepository.save(flight);
    }
}