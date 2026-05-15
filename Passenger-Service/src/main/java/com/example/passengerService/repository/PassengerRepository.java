package com.example.passengerService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.passengerService.entity.PassengerInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PassengerRepository extends JpaRepository<PassengerInfo, Long> {

    List<PassengerInfo> findByBookingId(UUID bookingId);

    Optional<PassengerInfo> findByPassengerId(Long passengerId);

    Optional<PassengerInfo> findByPassportNumber(String passportNumber);

    Optional<PassengerInfo> findByTicketNumber(String ticketNumber);

    List<PassengerInfo> findBySeatId(Long seatId);

    long countByBookingId(UUID bookingId);

    void deleteByBookingId(UUID bookingId);
}