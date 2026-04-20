package com.skybooker.auth.dto;

import com.skybooker.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer userId;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private String passportNumber;
    private String nationality;
    private Boolean isActive;
}