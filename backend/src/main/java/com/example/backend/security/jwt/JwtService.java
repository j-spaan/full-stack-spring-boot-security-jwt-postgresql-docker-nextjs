package com.example.backend.security.jwt;

/**
 * @author Jeffrey Spaan
 * @since 2024-06-10
 * @see com.example.backend.security.jwt.JwtServiceImpl
 */
public interface JwtService {
    String extractSubject(String jwt);
    String generateToken(String email);
    String generateRefreshToken(String email);
    boolean isTokenValid(String jwt, String email);
}