package com.skybooker.auth.service.impl;

import com.skybooker.auth.entity.User;
import com.skybooker.auth.service.AuthService;
import com.skybooker.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    @Override
    public User register(User user) {
        return userService.registerUser(user);
    }

    @Override
    public String login(String email, String password) {
        return userService.loginUser(email, password);
    }

    @Override
    public User getProfile(Integer userId) {
        return userService.getUserById(userId);
    }

    @Override
    public User updateProfile(Integer userId, User user) {
        return userService.updateUser(userId, user);
    }

    @Override
    public void logout() {
        System.out.println("Logout Successful");
    }
}