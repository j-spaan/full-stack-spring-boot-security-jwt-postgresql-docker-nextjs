package com.example.backend.security.password;

/**
 * @author Jeffrey Spaan
 * @since 2024-06-12
 * @see com.example.backend.security.password.PasswordServiceImpl
 */
public interface PasswordService {
    boolean isPasswordValid(String password, String storedPassword);
    String encodePassword(String password);
}