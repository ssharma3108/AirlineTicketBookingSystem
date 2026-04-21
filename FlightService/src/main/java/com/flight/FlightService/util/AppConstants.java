// AppConstants.java
package com.flight.FlightService.util;

public class AppConstants {

    private AppConstants() {
        // Prevent object creation
    }

    // API Base Path
    public static final String FLIGHT_BASE_URL = "/flights";

    // Default Pagination
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    // Seat Rules
    public static final int MIN_SEAT_BOOKING = 1;

    // Messages
    public static final String FLIGHT_NOT_FOUND = "Flight not found with id: ";
    public static final String FLIGHT_DELETED = "Flight deleted successfully";
    public static final String SEATS_NOT_AVAILABLE = "Requested seats not available";
    public static final String FLIGHT_CREATED = "Flight added successfully";
    public static final String FLIGHT_UPDATED = "Flight updated successfully";

    // Status Values
    public static final String STATUS_ON_TIME = "ON_TIME";
    public static final String STATUS_DELAYED = "DELAYED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_DEPARTED = "DEPARTED";
    public static final String STATUS_ARRIVED = "ARRIVED";
}