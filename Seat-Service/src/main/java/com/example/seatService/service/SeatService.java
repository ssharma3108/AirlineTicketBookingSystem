package com.example.seatService.service;

import com.example.seatService.dto.SeatRequestDTO;
import com.example.seatService.dto.SeatResponseDTO;
import com.example.seatService.entity.SeatClass;

import java.util.List;

public interface SeatService {

    List<SeatResponseDTO> addSeats(List<SeatRequestDTO> seats);

    List<SeatResponseDTO> getSeatsByFlight(Long flightId);

    List<SeatResponseDTO> getAvailableSeats(Long flightId);

    List<SeatResponseDTO> getByClass(Long flightId, SeatClass seatClass);

    SeatResponseDTO holdSeat(Long flightId, String seatNumber, String userId);

    SeatResponseDTO releaseSeat(Long flightId, String seatNumber);

    SeatResponseDTO confirmSeat(Long seatId);

    SeatResponseDTO lockSeat(Long flightId, String seatNumber);

    void deleteSeatsByFlight(Long flightId);
}
