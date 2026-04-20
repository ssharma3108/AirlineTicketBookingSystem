package com.skybooker.auth.controller;

import com.skybooker.auth.dto.AuthResponse;
import com.skybooker.auth.dto.LoginRequest;
import com.skybooker.auth.dto.RegisterRequest;
import com.skybooker.auth.entity.Role;
import com.skybooker.auth.entity.User;
import com.skybooker.auth.security.JwtUtil;
import com.skybooker.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword());
        user.setPhone(request.getPhone());
        user.setPassportNumber(request.getPassportNumber());
        user.setNationality(request.getNationality());
        user.setRole(Role.PASSENGER);

        authService.register(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse("Registration Successful", token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        authService.login(request.getEmail(), request.getPassword());

        String token = jwtUtil.generateToken(request.getEmail());

        return new AuthResponse("Login Successful", token);
    }

    @PostMapping("/logout")
    public String logout() {
        authService.logout();
        return "Logout Successful";
    }
}