package com.skybooker.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String passwordHash;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;

    private Boolean isActive;

    private String passportNumber;

    private String nationality;

    private LocalDateTime createdAt;

    private String otp;

    private LocalDateTime otpExpiry;

}

