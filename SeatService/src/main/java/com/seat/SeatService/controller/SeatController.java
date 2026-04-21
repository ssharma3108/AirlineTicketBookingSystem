package com.seat.SeatService.controller;

import com.seat.SeatService.entity.Seat;
import com.seat.SeatService.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService service;

    @PostMapping
    public Seat addSeat(@RequestBody Seat seat){
        return service.addSeat(seat);
    }

    @GetMapping("/{flightId}")
    public List<Seat> getSeats(@PathVariable Long flightId){
        return service.getSeatsByFlight(flightId);
    }

    @PutMapping("/hold/{seatId}")
    public Seat holdSeat(@PathVariable Long seatId){
        return service.holdSeat(seatId);
    }

    @PutMapping("/release/{seatId}")
    public Seat releaseSeat(@PathVariable Long seatId){
        return service.releaseSeat(seatId);
    }

    @PutMapping("/confirm/{seatId}")
    public Seat confirmSeat(@PathVariable Long seatId){
        return service.confirmSeat(seatId);
    }

    @DeleteMapping("/{flightId}")
    public String deleteSeats(@PathVariable Long flightId){
        service.deleteSeats(flightId);
        return "Deleted";
    }


    @PostMapping("/generate/{flightId}")
    public String generateSeats(@PathVariable Long flightId){
        service.generateSeatsForFlight(flightId);
        return "Seats generated successfully";
    }

    @GetMapping("/map/{flightId}")
    public List<Seat> getSeatMap(@PathVariable Long flightId){
        return service.getSeatsByFlight(flightId);
    }
}