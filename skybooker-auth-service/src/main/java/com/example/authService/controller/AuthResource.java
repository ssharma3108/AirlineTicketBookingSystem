package com.example.authService.controller;

import com.example.authService.dto.*;
import com.example.authService.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;

    //REGISTER
    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterDTO request) {
        return authService.register(request);
    }

    // LOGIN
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginDTO request) {
        return authService.login(request);
    }

    // LOGOUT
    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return "Logged out successfully";
    }

    // REFRESH TOKEN
    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestHeader("Authorization") String token) {
        return authService.refreshToken(token);
    }

    // 🔹 GET PROFILE
    @GetMapping("/profile")
    public UserDTO getProfile(@RequestHeader("Authorization") String token) {

        token = token.substring(7); // remove Bearer

        Long userId = authService.validateAndExtractUserId(token);

        return authService.getUserById(userId);
    }

    // UPDATE PROFILE
    @PutMapping("/profile")
    public UserDTO updateProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateProfileDTO request) {

        token = token.substring(7);

        Long userId = authService.validateAndExtractUserId(token);

        return authService.updateProfile(userId, request);
    }

    // CHANGE PASSWORD
    @PutMapping("/password")
    public String changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordDTO request) {

        token = token.substring(7);

        Long userId = authService.validateAndExtractUserId(token);

        authService.changePassword(userId, request);

        return "Password updated successfully";
    }

    @PostMapping("/password/forgot/request-otp")
    public Map<String, String> requestForgotPasswordOtp(@Valid @RequestBody ForgotPasswordRequestDTO request) {
        authService.requestForgotPasswordOtp(request);
        return Map.of("message", "If your email exists, an OTP has been sent.");
    }

    @PostMapping("/password/forgot/verify-otp")
    public Map<String, String> verifyForgotPasswordOtp(@Valid @RequestBody VerifyOtpDTO request) {
        authService.verifyForgotPasswordOtp(request);
        return Map.of("message", "OTP verified successfully.");
    }

    @PostMapping("/password/forgot/reset")
    public Map<String, String> resetPasswordWithOtp(@Valid @RequestBody ResetPasswordWithOtpDTO request) {
        authService.resetPasswordWithOtp(request);
        return Map.of("message", "Password reset successful. Please login again.");
    }

    // DEACTIVATE ACCOUNT
    @PutMapping("/deactivate")
    public String deactivate(@RequestHeader("Authorization") String token) {

        token = token.substring(7);

        Long userId = authService.validateAndExtractUserId(token);

        authService.deactivateAccount(userId);

        return "Account deactivated";
    }

    // ADMIN gets all users. STAFF gets only passenger users.
    @GetMapping("/users")
    public List<UserDTO> getAllUsers(
            @RequestHeader(value = "X-Role", required = false) String role) {

        List<UserDTO> users = authService.getAllUsers();

        if ("STAFF".equals(role)) {
            return users.stream()
                    .filter(user -> "PASSENGER".equals(user.role))
                    .toList();
        }

        return users;
    }

    @PutMapping("/users/{userId}")
    public UserDTO adminUpdateUser(@PathVariable Long userId,
                                   @RequestBody AdminUserUpdateDTO request) {
        return authService.adminUpdateUser(userId, request);
    }

    @DeleteMapping("/users/{userId}")
    public Map<String, String> adminDeleteUser(@PathVariable Long userId) {
        authService.adminDeleteUser(userId);
        return Map.of("message", "User deleted successfully");
    }
}