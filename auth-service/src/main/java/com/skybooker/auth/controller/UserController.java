package com.skybooker.auth.controller;

import com.skybooker.auth.entity.User;
import com.skybooker.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Integer userId,
                           @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @PutMapping("/deactivate/{userId}")
    public String deactivateUser(@PathVariable Integer userId) {
        userService.deactivateUser(userId);
        return "User Deactivated Successfully";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return "User Deleted Successfully";
    }
}