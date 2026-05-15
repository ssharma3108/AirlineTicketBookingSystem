package com.example.authService.service;

import com.example.authService.dto.*;
import com.example.authService.entity.Role;
import com.example.authService.entity.User;
import com.example.authService.repository.UserRepository;
import com.example.authService.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JwtUtil jwtUtil = new JwtUtil();

    private final Map<String, OtpEntry> otpStore = new ConcurrentHashMap<>();
    private static final long OTP_EXPIRY_MINUTES = 10;

    // REGISTER
    @Override
    public AuthResponse register(RegisterDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPasswordHash(encoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setPassportNumber(request.getPassportNumber());
        user.setNationality(request.getNationality());
        user.setRole(Role.PASSENGER);
        user.setProvider("LOCAL");
        user.setActive(true);

        userRepository.save(user);

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getUserId().intValue()
        );

        return new AuthResponse(token, user.getRole().name(), user.getUserId());
    }

    // LOGIN
    @Override
    public AuthResponse login(LoginDTO request) {
        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!user.isActive()) {
            throw new RuntimeException("Account is deactivated");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getUserId().intValue()
        );

        return new AuthResponse(token, user.getRole().name(), user.getUserId());
    }

    // LOGOUT (JWT stateless)
    @Override
    public void logout(String token) {
        // Frontend discards token.
    }

    // REFRESH TOKEN
    @Override
    public AuthResponse refreshToken(String token) {
        token = token.replace("Bearer ", "");

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }

        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newToken = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getUserId().intValue()
        );

        return new AuthResponse(newToken, user.getRole().name(), user.getUserId());
    }

    // GET USER BY ID
    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToDTO(user);
    }

    // UPDATE PROFILE
    @Override
    public UserDTO updateProfile(Long userId, UpdateProfileDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setNationality(request.getNationality());

        userRepository.save(user);
        return mapToDTO(user);
    }

    // ADMIN UPDATE USER
    @Override
    public UserDTO adminUpdateUser(Long userId, AdminUserUpdateDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.fullName != null) user.setFullName(request.fullName);
        if (request.phone != null) user.setPhone(request.phone);
        if (request.passportNumber != null) user.setPassportNumber(request.passportNumber);
        if (request.nationality != null) user.setNationality(request.nationality);
        if (request.role != null) user.setRole(Role.valueOf(request.role));

        return mapToDTO(userRepository.save(user));
    }

    // ADMIN DELETE USER
    @Override
    public void adminDeleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    // CHANGE PASSWORD (logged in user)
    @Override
    public void changePassword(Long userId, ChangePasswordDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getOldPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPasswordHash(encoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    // FORGOT PASSWORD - REQUEST OTP
    @Override
    public void requestForgotPasswordOtp(ForgotPasswordRequestDTO request) {
        String email = request.getEmail().trim().toLowerCase();
        User user = userRepository.findByEmail(email).orElse(null);

        // Do not reveal if email exists
        if (user == null) {
            return;
        }

        String otp = String.valueOf((int) (100000 + Math.random() * 900000));
        OtpEntry entry = new OtpEntry(otp, LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES), false);
        otpStore.put(email, entry);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("SkyBooker Password Reset OTP");
        message.setText("Your SkyBooker OTP is " + otp + ". It is valid for 10 minutes.");
        mailSender.send(message);
    }

    // FORGOT PASSWORD - VERIFY OTP
    @Override
    public void verifyForgotPasswordOtp(VerifyOtpDTO request) {
        String email = request.getEmail().trim().toLowerCase();
        OtpEntry entry = otpStore.get(email);

        if (entry == null) {
            throw new RuntimeException("OTP not requested for this email");
        }

        if (entry.expiresAt().isBefore(LocalDateTime.now())) {
            otpStore.remove(email);
            throw new RuntimeException("OTP expired. Please request again.");
        }

        if (!entry.otp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        otpStore.put(email, new OtpEntry(entry.otp(), entry.expiresAt(), true));
    }

    // FORGOT PASSWORD - RESET
    @Override
    public void resetPasswordWithOtp(ResetPasswordWithOtpDTO request) {
        String email = request.getEmail().trim().toLowerCase();
        OtpEntry entry = otpStore.get(email);

        if (entry == null) {
            throw new RuntimeException("OTP not requested for this email");
        }

        if (entry.expiresAt().isBefore(LocalDateTime.now())) {
            otpStore.remove(email);
            throw new RuntimeException("OTP expired. Please request again.");
        }

        if (!entry.otp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (!entry.verified()) {
            throw new RuntimeException("Please verify OTP first");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPasswordHash(encoder.encode(request.getNewPassword()));
        userRepository.save(user);
        otpStore.remove(email);
    }

    // DEACTIVATE ACCOUNT
    @Override
    public void deactivateAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);
        userRepository.save(user);
    }

    // GET ALL USERS
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // VALIDATE + EXTRACT USER ID FROM TOKEN
    @Override
    public Long validateAndExtractUserId(String token) {
        token = token.replace("Bearer ", "");

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid token");
        }

        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getUserId();
    }

    // MAPPER  ->>>>>>User entity into a UserDTO
    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole().name());
        dto.setPassportNumber(user.getPassportNumber());
        dto.setNationality(user.getNationality());
        return dto;
    }

    private record OtpEntry(String otp, LocalDateTime expiresAt, boolean verified) {
    }
}
