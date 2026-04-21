package com.booking.BookingService.repository;

import com.booking.BookingService.entity.Booking;
import com.booking.BookingService.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    // Find all bookings of one user
    List<Booking> findByUserId(UUID userId);

    // Find booking using PNR code
    Optional<Booking> findByPnrCode(String pnrCode);

    // Find all bookings of a flight
    List<Booking> findByFlightId(UUID flightId);

    // Find bookings by status
    List<Booking> findByStatus(BookingStatus status);

    // Count bookings by flight and status
    long countByFlightIdAndStatus(UUID flightId, BookingStatus status);

    // User bookings by status
    List<Booking> findByUserIdAndStatus(UUID userId, BookingStatus status);
}