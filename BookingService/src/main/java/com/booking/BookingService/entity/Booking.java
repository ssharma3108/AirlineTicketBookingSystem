package com.booking.BookingService.entity;

import com.booking.BookingService.enums.BookingStatus;
import com.booking.BookingService.enums.TripType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bookingId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID flightId;

    @Column(unique = true, nullable = false, length = 6)
    private String pnrCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripType tripType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private Double totalFare;

    private Double baseFare;

    private Double taxes;

    private String mealPreference;

    private Integer luggageKg;

    private String contactEmail;

    private String contactPhone;

    private LocalDateTime bookedAt;

    private UUID paymentId;
}