package com.example.authService.dto;

public class AuthResponse {
	public String token;
    public String role;
    public Long userId;

    public AuthResponse(String token, String role, Long userId) {
        this.token = token;
        this.role = role;
        this.userId = userId;
    }
}
