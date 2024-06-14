package com.example.backend.security.auth;

import com.example.backend.payload.request.AuthRegistrationRequest;
import com.example.backend.payload.request.AuthRequest;
import com.example.backend.payload.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * @author Jeffrey Spaan
 * @since 2024-06-11
 * @see com.example.backend.security.auth.AuthServiceImpl
 */
public interface AuthService {
    boolean isAuthenticationValid(Authentication authentication);
    void setAuthentication(String userEmail, HttpServletRequest request);
    AuthResponse register(AuthRegistrationRequest authRegistrationRequest);
    AuthResponse authenticate(AuthRequest authRequest);
    AuthResponse refreshToken();
}