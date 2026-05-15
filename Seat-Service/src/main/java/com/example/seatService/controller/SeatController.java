package com.example.seatService.controller;

import com.example.seatService.dto.SeatRequestDTO;
import com.example.seatService.dto.SeatResponseDTO;
import com.example.seatService.entity.SeatClass;
import com.example.seatService.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/add")
    public List<SeatResponseDTO> addSeats(@Valid @RequestBody List<SeatRequestDTO> seatRequestDTOs) {
        return seatService.addSeats(seatRequestDTOs);
    }

    @GetMapping("/flight/{flightId}")
    public List<SeatResponseDTO> getSeatsByFlight(@PathVariable Long flightId) {
        return seatService.getSeatsByFlight(flightId);
    }

    @GetMapping("/available/{flightId}")
    public List<SeatResponseDTO> getAvailableSeats(@PathVariable Long flightId) {
        return seatService.getAvailableSeats(flightId);
    }

    @GetMapping("/class")
    public List<SeatResponseDTO> getSeatsByClass(@RequestParam Long flightId,
                                                 @RequestParam SeatClass seatClass) {
        return seatService.getByClass(flightId, seatClass);
    }

    @PutMapping("/hold")
    public SeatResponseDTO holdSeat(@RequestParam Long flightId,
                                    @RequestParam String seatNumber,
                                    @RequestHeader("X-User-Id") String userId) {
        return seatService.holdSeat(flightId, seatNumber, userId);
    }

    @PutMapping("/release")
    public SeatResponseDTO releaseSeat(@RequestParam Long flightId,
                                       @RequestParam String seatNumber) {
        return seatService.releaseSeat(flightId, seatNumber);
    }

    @PutMapping("/confirm/{seatId}")
    public SeatResponseDTO confirmSeat(@PathVariable Long seatId) {
        return seatService.confirmSeat(seatId);
    }

    @PostMapping("/lock")
    public SeatResponseDTO lockSeat(@RequestParam Long flightId,
                                    @RequestParam String seatNumber) {
        return seatService.lockSeat(flightId, seatNumber);
    }

    @DeleteMapping("/flight/{flightId}")
    public void deleteSeatsByFlight(@PathVariable Long flightId) {
        seatService.deleteSeatsByFlight(flightId);
    }
}
