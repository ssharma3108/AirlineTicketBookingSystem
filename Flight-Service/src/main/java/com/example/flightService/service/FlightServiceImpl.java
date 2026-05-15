package com.example.flightService.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.flightService.dto.FlightDTO;
import com.example.flightService.entity.Flight;
import com.example.flightService.entity.FlightStatus;
import com.example.flightService.repository.FlightRepository;


@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    //DTO - Entity
    private Flight mapToEntity(FlightDTO dto) {
        Flight f = new Flight();

        f.setFlightNumber(dto.getFlightNumber());
        f.setAirlineId(dto.getAirlineId());
        f.setOriginAirportCode(dto.getOriginAirportCode());
        f.setDestinationAirportCode(dto.getDestinationAirportCode());
        f.setDepartureTime(dto.getDepartureTime());
        f.setArrivalTime(dto.getArrivalTime());
        f.setDurationMinutes(dto.getDurationMinutes());
        f.setStatus(dto.getStatus());
        f.setAircraftType(dto.getAircraftType());
        f.setTotalSeats(dto.getTotalSeats());
        f.setAvailableSeats(dto.getAvailableSeats());
        f.setBasePrice(dto.getBasePrice());

        return f;
    }

    // Entity - DTO
    private FlightDTO mapToDTO(Flight f) {
        FlightDTO dto = new FlightDTO();

        dto.setId(f.getFlightId());
        dto.setFlightNumber(f.getFlightNumber());
        dto.setAirlineId(f.getAirlineId());
        dto.setOriginAirportCode(f.getOriginAirportCode());
        dto.setDestinationAirportCode(f.getDestinationAirportCode());
        dto.setDepartureTime(f.getDepartureTime());
        dto.setArrivalTime(f.getArrivalTime());
        dto.setDurationMinutes(f.getDurationMinutes());
        dto.setStatus(f.getStatus());
        dto.setAircraftType(f.getAircraftType());
        dto.setTotalSeats(f.getTotalSeats());
        dto.setAvailableSeats(f.getAvailableSeats());
        dto.setBasePrice(f.getBasePrice());

        return dto;
    }

    // Update helper
    private void updateEntity(Flight f, FlightDTO dto) {
        f.setFlightNumber(dto.getFlightNumber());
        f.setAirlineId(dto.getAirlineId());
        f.setOriginAirportCode(dto.getOriginAirportCode());
        f.setDestinationAirportCode(dto.getDestinationAirportCode());
        f.setDepartureTime(dto.getDepartureTime());
        f.setArrivalTime(dto.getArrivalTime());
        f.setDurationMinutes(dto.getDurationMinutes());
        f.setStatus(dto.getStatus());
        f.setAircraftType(dto.getAircraftType());
        f.setTotalSeats(dto.getTotalSeats());
//        f.setAvailableSeats(dto.getAvailableSeats());
        f.setBasePrice(dto.getBasePrice());
    }

    //Add Flight
    @Override
    public FlightDTO addFlight(FlightDTO dto) {
        validateFlightDates(dto);
        Flight saved = flightRepository.save(mapToEntity(dto));
        return mapToDTO(saved);
    }

    //Get by ID
    @Override
    public FlightDTO getFlightById(Long id) {
        Flight f = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return mapToDTO(f);
    }

    //Get by Flight Number
    @Override
    public FlightDTO getFlightByNumber(String flightNumber) {
        Flight f = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        return mapToDTO(f);
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    //Search Flights
    @Override
    public List<FlightDTO> searchFlights(String origin, String dest, LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        return flightRepository.searchFlights(origin, dest, start, end)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    //Round Trip
    @Override
    public Map<String, List<FlightDTO>> searchRoundTrip(
            String origin,
            String dest,
            LocalDate departDate,
            LocalDate returnDate) {

        Map<String, List<FlightDTO>> result = new HashMap<>();

        result.put("onward", searchFlights(origin, dest, departDate));
        result.put("return", searchFlights(dest, origin, returnDate));

        return result;
    }

    //Update Flight
    @Override
    public FlightDTO updateFlight(Long id, FlightDTO dto) {
        Flight f = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        validateFlightDates(dto);
        updateEntity(f, dto);
        return mapToDTO(flightRepository.save(f));
    }

    //Update Status
    @Override
    public FlightDTO updateStatus(Long id, FlightStatus status) {
        Flight f = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        f.setStatus(status);
        return mapToDTO(flightRepository.save(f));
    }

    // Decrement Seats
    @Override
    public void decrementSeats(Long id, int count) {
        Flight f = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (f.getAvailableSeats() < count) {
            throw new RuntimeException("Not enough seats");
        }

        f.setAvailableSeats(f.getAvailableSeats() - count);
        flightRepository.save(f);
    }

    //Increment Seats
    @Override
    public void incrementSeats(Long id, int count) {
        Flight f = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (f.getAvailableSeats() + count > f.getTotalSeats()) {
            throw new RuntimeException("Seat limit exceeded");
        }

        f.setAvailableSeats(f.getAvailableSeats() + count);
        flightRepository.save(f);
    }

    //Delete
    @Override
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    // By Airline
    @Override
    public List<FlightDTO> getFlightsByAirline(Long airlineId) {
        return flightRepository.findByAirlineId(airlineId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private void validateFlightDates(FlightDTO dto) {
        if (dto.getDepartureTime() == null || dto.getArrivalTime() == null) {
            throw new RuntimeException("Departure and arrival time are required");
        }

        if (dto.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Departure time cannot be before current date and time");
        }

        if (!dto.getArrivalTime().isAfter(dto.getDepartureTime())) {
            throw new RuntimeException("Arrival time must be after departure time");
        }

        if (dto.getAvailableSeats() != null
                && dto.getTotalSeats() != null
                && dto.getAvailableSeats() > dto.getTotalSeats()) {
            throw new RuntimeException("Available seats cannot be greater than total seats");
        }
    }
}