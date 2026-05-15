package com.example.authService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.authService.entity.Role;
import com.example.authService.entity.User;
import java.util.*;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(Long userId);

    boolean existsByEmail(String email);

    List<User> findAllByRole(Role role);

    Optional<User> findByPhone(String phone);

    Optional<User> findByPassportNumber(String passportNumber);

    void deleteByUserId(Long userId);
}