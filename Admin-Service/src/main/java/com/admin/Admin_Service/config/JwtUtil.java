package com.admin.Admin_Service.config;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "admin_secret_key";

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    public Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        return extractClaims(token).getExpiration().after(new Date());
    }
}