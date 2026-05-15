package com.example.airlineService.entity;

import jakarta.persistence.*;

@Entity
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airlineId;

    private String name;

    @Column(unique = true)
    private String iataCode;

    private String icaoCode;
    private String logoUrl;
    private String country;
    private String contactEmail;
    private String contactPhone;
    private boolean isActive = true;

    // getters & setters

    public Long getAirlineId() { return airlineId; }
    public void setAirlineId(Long airlineId) { this.airlineId = airlineId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIataCode() { return iataCode; }
    public void setIataCode(String iataCode) { this.iataCode = iataCode; }

    public String getIcaoCode() { return icaoCode; }
    public void setIcaoCode(String icaoCode) { this.icaoCode = icaoCode; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}