package com.skybooker.auth.repository;

import com.skybooker.auth.entity.Role;
import com.skybooker.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find user by email (for login)
    Optional<User> findByEmail(String email);

    // Check email already exists (for registration)
    boolean existsByEmail(String email);

    // Find users by role
    List<User> findByRole(Role role);

    // Find active users
    List<User> findByIsActiveTrue();

    // Find inactive users
    List<User> findByIsActiveFalse();

    // Find by passport number
    Optional<User> findByPassportNumber(String passportNumber);

    // Find by phone number
    Optional<User> findByPhone(String phone);

}