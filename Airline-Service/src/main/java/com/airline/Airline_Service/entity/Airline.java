package com.airline.Airline_Service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "airlines", uniqueConstraints = {
        @UniqueConstraint(columnNames = "iataCode"),
        @UniqueConstraint(columnNames = "icaoCode")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airlineId;

    private String name;

    @Column(nullable = false, unique = true)
    private String iataCode;

    @Column(nullable = false, unique = true)
    private String icaoCode;

    private String logoUrl;
    private String country;

    private String contactEmail;
    private String contactPhone;

    private Boolean isActive = true;
}