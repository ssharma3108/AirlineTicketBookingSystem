package com.skybooker.auth.service;

import com.skybooker.auth.entity.User;

import java.util.List;

public interface UserService {

    User registerUser(User user);

    String loginUser(String email, String password);

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(Integer userId, User user);

    void deactivateUser(Integer userId);

    void deleteUser(Integer userId);

    void forgotPassword(String email);

    void verifyOtpAndResetPassword(String email, String otp, String newPassword);

}