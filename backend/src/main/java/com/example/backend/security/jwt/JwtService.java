package com.example.backend.security.jwt;

public interface JwtService {
    String extractSubject(String jwt);
    String generateToken(String email);
    String generateRefreshToken(String email);
    boolean isTokenValid(String jwt, String email);
}
