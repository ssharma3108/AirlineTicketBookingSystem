package com.example.authService.service;

import com.example.authService.dto.*;
import java.util.*;

public interface AuthService {

    AuthResponse register(RegisterDTO request);

    AuthResponse login(LoginDTO request);

    void logout(String token);

    AuthResponse refreshToken(String token);

    UserDTO getUserById(Long userId);

    UserDTO updateProfile(Long userId, UpdateProfileDTO request);

    UserDTO adminUpdateUser(Long userId, AdminUserUpdateDTO request);

    void adminDeleteUser(Long userId);

    void changePassword(Long userId, ChangePasswordDTO request);

    void requestForgotPasswordOtp(ForgotPasswordRequestDTO request);

    void verifyForgotPasswordOtp(VerifyOtpDTO request);

    void resetPasswordWithOtp(ResetPasswordWithOtpDTO request);

    void deactivateAccount(Long userId);

    List<UserDTO> getAllUsers();

    Long validateAndExtractUserId(String token);
}