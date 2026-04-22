package com.passenger.PassengerService.service;

import com.passenger.PassengerService.dto.PassengerRequestDTO;
import com.passenger.PassengerService.dto.PassengerResponseDTO;
import com.passenger.PassengerService.entity.Passenger;

import java.util.List;

public interface PassengerService {

    PassengerResponseDTO addPassenger(PassengerRequestDTO dto);

    Passenger getPassengerById(Long id);

    List<Passenger> getPassengersByBooking(Long bookingId);

    Passenger updatePassenger(Long id, PassengerRequestDTO dto);

    Passenger assignSeat(Long passengerId, Long seatId);

    void deletePassenger(Long id);

    long getPassengerCount(Long bookingId);
}