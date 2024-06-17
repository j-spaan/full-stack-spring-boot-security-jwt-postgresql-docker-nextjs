package com.example.backend.security.auth;

import com.example.backend.payload.request.AuthRegistrationRequest;
import com.example.backend.payload.request.AuthLoginRequest;
import com.example.backend.payload.response.AuthResponse;
import org.springframework.security.core.Authentication;

/**
 * @author Jeffrey Spaan
 * @since 2024-06-11
 * @see com.example.backend.security.auth.AuthServiceImpl
 */
public interface AuthService {
    boolean isAuthenticationValid(Authentication authentication);
    AuthResponse register(AuthRegistrationRequest authRegistrationRequest);
    AuthResponse authenticate(AuthLoginRequest authLoginRequest);
    AuthResponse refreshToken();
}