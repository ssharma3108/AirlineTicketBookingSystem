package com.skybooker.auth.controller;

import com.skybooker.auth.dto.ForgotPasswordRequest;
import com.skybooker.auth.dto.VerifyOtpRequest;
import com.skybooker.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {

    private final UserService userService;

    @PostMapping("/forgot")
    public String forgotPassword(@RequestBody ForgotPasswordRequest request) {
        userService.forgotPassword(request.getEmail());
        return "OTP Sent Successfully";
    }

    @PostMapping("/reset")
    public String resetPassword(@RequestBody VerifyOtpRequest request) {

        userService.verifyOtpAndResetPassword(
                request.getEmail(),
                request.getOtp(),
                request.getNewPassword()
        );

        return "Password Reset Successful";
    }
}