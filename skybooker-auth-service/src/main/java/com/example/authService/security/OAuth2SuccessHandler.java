package com.example.authService.security;


import com.example.authService.entity.User;
import com.example.authService.repository.UserRepository;
import com.example.authService.entity.Role;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setFullName(name);
            user.setRole(Role.PASSENGER);
            user.setProvider("GOOGLE");
            user.setActive(true);

            userRepository.save(user);
        }

        String token = jwtUtil.generateToken(
        	    user.getEmail(),
        	    user.getRole().name(),
        	    user.getUserId().intValue()
        	);

        // Redirect with token  ->browser redirects to Angular frontend.
        response.sendRedirect("http://localhost:4200/oauth-success?token=" + token);
    }
}