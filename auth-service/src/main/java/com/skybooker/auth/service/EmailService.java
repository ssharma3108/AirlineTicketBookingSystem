package com.skybooker.auth.service;

public interface EmailService {
    void sendOtp(String toEmail, String otp);
}