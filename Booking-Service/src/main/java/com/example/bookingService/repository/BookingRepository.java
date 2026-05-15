package com.example.bookingService.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookingService.entity.Booking;
import com.example.bookingService.entity.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    Optional<Booking> findByPnrCode(String pnrCode);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByFlightId(Long flightId);

    List<Booking> findByStatus(BookingStatus status);

    long countByFlightIdAndStatus(Long flightId, BookingStatus status);

    List<Booking> findByUserIdAndStatus(Long userId, BookingStatus status);
}