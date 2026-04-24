package com.airline.Airline_Service.contants;

public class AppConstants {

    // ================= GENERAL =================
    public static final String SUCCESS = "Success";
    public static final String FAILED = "Failed";

    // ================= AIRLINE =================
    public static final String AIRLINE_NOT_FOUND = "Airline not found";
    public static final String AIRLINE_DEACTIVATED = "Airline deactivated successfully";

    // ================= AIRPORT =================
    public static final String AIRPORT_NOT_FOUND = "Airport not found";

    // ================= VALIDATION =================
    public static final String INVALID_IATA = "Invalid IATA Code";
    public static final String INVALID_ICAO = "Invalid ICAO Code";

    // ================= API =================
    public static final String BASE_API = "/api/v1";

    private AppConstants() {
        // Prevent instantiation
    }
}