package com.example.authService.service;

import com.example.authService.dto.*;
import com.example.authService.entity.Role;
import com.example.authService.entity.User;
import com.example.authService.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private AuthServiceImpl service;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void login_validCredentials_returnsToken() {
        LoginDTO req = new LoginDTO();
        req.setEmail("a@b.com");
        req.setPassword("secret");

        User u = new User();
        u.setEmail("a@b.com");
        u.setPasswordHash(encoder.encode("secret"));
        u.setActive(true);

        u.setRole(Role.PASSENGER);

        when(userRepository.findByEmail("a@b.com")).thenReturn(Optional.of(u));

        AuthResponse res = service.login(req);

        assertNotNull(res);
        assertNotNull(res.token);          // use public field (no getter in your DTO)
        assertEquals("PASSENGER", res.role);
        assertEquals(1L, res.userId);
    }

    @Test
    void login_wrongPassword_throwsError() {
        LoginDTO req = new LoginDTO();
        req.setEmail("a@b.com");
        req.setPassword("wrongpass");

        User u = new User();
        u.setEmail("a@b.com");
        u.setPasswordHash(encoder.encode("secret"));
        u.setActive(true);

        u.setRole(Role.PASSENGER);

        when(userRepository.findByEmail("a@b.com")).thenReturn(Optional.of(u));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.login(req));
        assertEquals("Invalid credentials", ex.getMessage());
    }

    @Test
    void register_newUser_success() {
        RegisterDTO req = new RegisterDTO();
        req.setFullName("Kartik");
        req.setEmail("kartik@test.com");
        req.setPassword("secret123");
        req.setPhone("9876543210");
        req.setPassportNumber("AB12345");
        req.setNationality("Indian");

        when(userRepository.existsByEmail("kartik@test.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> {
            User saved = inv.getArgument(0);
           // simulate DB-generated ID
            return saved;
        });

        AuthResponse res = service.register(req);

        assertNotNull(res);
        assertNotNull(res.token);
        assertEquals("PASSENGER", res.role);
        assertEquals(10L, res.userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_existingEmail_throwsError() {
        RegisterDTO req = new RegisterDTO();
        req.setEmail("exists@test.com");

        when(userRepository.existsByEmail("exists@test.com")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.register(req));
        assertEquals("Email already exists", ex.getMessage());
    }

    @Test
    void requestForgotPasswordOtp_userNotFound_noMailSent() {
        ForgotPasswordRequestDTO req = new ForgotPasswordRequestDTO();
        req.setEmail("nouser@test.com");

        when(userRepository.findByEmail("nouser@test.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> service.requestForgotPasswordOtp(req));
        verify(mailSender, never()).send(any(org.springframework.mail.SimpleMailMessage.class));
    }

    @Test
    void requestForgotPasswordOtp_userExists_mailSent() {
        ForgotPasswordRequestDTO req = new ForgotPasswordRequestDTO();
        req.setEmail("user@test.com");

        User user = new User();

        user.setEmail("user@test.com");
        user.setActive(true);
        user.setRole(Role.PASSENGER);

        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));

        service.requestForgotPasswordOtp(req);

        verify(mailSender, times(1)).send(any(org.springframework.mail.SimpleMailMessage.class));
    }
}
