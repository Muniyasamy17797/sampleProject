package com.project.app.infrastructure.utils;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String key;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        // Decode the base64-encoded key
            byte[] keyBytes = Base64.getDecoder().decode(key); // Must be base64-encoded
            secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // 1. Generate JWT token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)                         // who this token belongs to
                .setIssuedAt(new Date())                      // issue time
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, secretKey)   // sign with secret
                .compact();
    }

    // 2. Extract username from token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 3. Check if token is expired
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    // 4. Validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    
}
