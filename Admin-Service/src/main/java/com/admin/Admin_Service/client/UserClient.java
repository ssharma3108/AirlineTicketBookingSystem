package com.admin.Admin_Service.client;

import com.admin.Admin_Service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/api/users")
    List<UserDTO> getAllUsers();

    @PutMapping("/api/users/{id}/suspend")
    String suspendUser(@PathVariable Long id);

    @DeleteMapping("/api/users/{id}")
    String deleteUser(@PathVariable Long id);

    @GetMapping("/api/users/count")
    Long getUserCount();
}