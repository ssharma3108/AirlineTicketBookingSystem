package com.example.passengerService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.passengerService.dto.PassengerDTO;
import com.example.passengerService.service.PassengerService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerService service;

    // ADD PASSENGER
    @PostMapping
    public PassengerDTO addPassenger(@Valid @RequestBody PassengerDTO dto) {
        return service.addPassenger(dto);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public PassengerDTO getPassenger(@PathVariable Long id) {
        return service.getPassengerById(id);
    }

    // GET BY BOOKING ID
    @GetMapping("/booking/{bookingId}")
    public List<PassengerDTO> getByBooking(@PathVariable UUID bookingId) {
        return service.getPassengersByBooking(bookingId);
    }

    // GET BY PASSPORT
    @GetMapping("/passport/{passportNumber}")
    public PassengerDTO getByPassport(@PathVariable String passportNumber) {
        return service.getByPassportNumber(passportNumber);
    }

    // UPDATE PASSENGER
    @PutMapping("/{id}")
    public PassengerDTO updatePassenger(@PathVariable Long id,
                                        @RequestBody PassengerDTO dto) {
        return service.updatePassenger(id, dto);
    }

    // ASSIGN SEAT (web check-in)
    @PutMapping("/{id}/seat")
    public PassengerDTO assignSeat(@PathVariable Long id,
                                   @RequestParam Long seatId,
                                   @RequestParam String seatNumber) {
        return service.assignSeat(id, seatId, seatNumber);
    }

    // GET COUNT BY BOOKING
    @GetMapping("/count/{bookingId}")
    public long getCount(@PathVariable UUID bookingId) {
        return service.getPassengerCount(bookingId);
    }

    // DELETE PASSENGER
    @DeleteMapping("/{id}")
    public String deletePassenger(@PathVariable Long id) {
        service.deletePassenger(id);
        return "Passenger deleted successfully";
    }
}