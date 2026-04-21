package com.seat.SeatService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private Long flightId;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatClass seatClass;

    @Column(name = "seat_row")
    private Integer seatRow;

    @Column(name = "seat_column")
    private String seatColumn;

    private boolean isWindow;

    private boolean isAisle;

    private boolean hasExtraLegroom;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    private Double priceMultiplier;

    private LocalDateTime holdTime;
}