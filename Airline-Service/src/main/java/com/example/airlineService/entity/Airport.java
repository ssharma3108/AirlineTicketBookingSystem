package com.example.airlineService.entity;

import jakarta.persistence.*;

@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;

    private String name;

    @Column(unique = true)
    private String iataCode;

    private String icaoCode;
    private String city;
    private String country;
    private double latitude;
    private double longitude;
    private String timezone;

    // getters & setters

    public Long getAirportId() { return airportId; }
    public void setAirportId(Long airportId) { this.airportId = airportId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIataCode() { return iataCode; }
    public void setIataCode(String iataCode) { this.iataCode = iataCode; }

    public String getIcaoCode() { return icaoCode; }
    public void setIcaoCode(String icaoCode) { this.icaoCode = icaoCode; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
}