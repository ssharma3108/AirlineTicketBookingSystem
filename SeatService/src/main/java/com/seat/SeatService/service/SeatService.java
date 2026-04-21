package com.seat.SeatService.service;

import com.seat.SeatService.entity.Seat;

import java.util.List;

public interface SeatService {

    Seat addSeat(Seat seat);

    List<Seat> getSeatsByFlight(Long flightId);

    Seat holdSeat(Long seatId);

    Seat releaseSeat(Long seatId);

    Seat confirmSeat(Long seatId);

    void deleteSeats(Long flightId);

    void generateSeatsForFlight(Long flightId);

}