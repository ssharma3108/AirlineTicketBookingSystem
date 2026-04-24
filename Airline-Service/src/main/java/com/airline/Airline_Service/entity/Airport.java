package com.airline.Airline_Service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "airports", uniqueConstraints = {
        @UniqueConstraint(columnNames = "iataCode"),
        @UniqueConstraint(columnNames = "icaoCode")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;

    private String name;

    @Column(nullable = false, unique = true)
    private String iataCode;

    private String icaoCode;

    private String city;
    private String country;

    private Double latitude;
    private Double longitude;

    private String timezone;
}
