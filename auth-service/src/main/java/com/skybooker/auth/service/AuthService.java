package com.skybooker.auth.service;

import com.skybooker.auth.entity.User;

public interface AuthService {

    User register(User user);

    String login(String email, String password);

    User getProfile(Integer userId);

    User updateProfile(Integer userId, User user);

    void logout();
}