package com.passenger.PassengerService.controller;

import com.passenger.PassengerService.dto.PassengerRequestDTO;
import com.passenger.PassengerService.dto.PassengerResponseDTO;
import com.passenger.PassengerService.dto.SeatAssignmentDTO;
import com.passenger.PassengerService.entity.Passenger;
import com.passenger.PassengerService.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService service;

    @PostMapping
    public PassengerResponseDTO addPassenger(@RequestBody PassengerRequestDTO dto) {
        return service.addPassenger(dto);
    }

    @GetMapping("/{id}")
    public Passenger getPassenger(@PathVariable Long id) {
        return service.getPassengerById(id);
    }

    @GetMapping("/booking/{bookingId}")
    public List<Passenger> getByBooking(@PathVariable Long bookingId) {
        return service.getPassengersByBooking(bookingId);
    }

    @PutMapping("/{id}")
    public Passenger updatePassenger(@PathVariable Long id,
                                     @RequestBody PassengerRequestDTO dto) {
        return service.updatePassenger(id, dto);
    }

    @PutMapping("/assign-seat")
    public Passenger assignSeat(@RequestBody SeatAssignmentDTO dto) {
        return service.assignSeat(dto.getPassengerId(), dto.getSeatId());
    }

    @DeleteMapping("/{id}")
    public String deletePassenger(@PathVariable Long id) {
        service.deletePassenger(id);
        return "Passenger deleted";
    }

    @GetMapping("/count/{bookingId}")
    public long count(@PathVariable Long bookingId) {
        return service.getPassengerCount(bookingId);
    }
}