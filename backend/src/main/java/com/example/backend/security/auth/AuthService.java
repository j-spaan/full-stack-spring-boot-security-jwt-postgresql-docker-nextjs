package com.example.backend.security.auth;

import org.springframework.security.core.Authentication;

/**
 * @author Jeffrey Spaan
 * @since 2024-06-11
 * @see com.example.backend.security.auth.AuthServiceImpl
 */
public interface AuthService {
    boolean isAuthenticationValid(Authentication authentication);
}