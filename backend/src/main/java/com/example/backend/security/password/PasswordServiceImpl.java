package com.example.backend.security.password;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for handling password related operations.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link PasswordEncoder}: Spring Security's interface for encoding passwords in a secure manner.</li>
 * </ul>
 *
 * @author Jeffrey Spaan
 * @since 2024-06-12
 */
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    /**
     * Checks if the provided password matches the stored password.
     *
     * @param password the password provided by the user
     * @param storedPassword the password stored in the database
     * @return true if the passwords match, false otherwise
     */
    @Override
    public boolean isPasswordValid(String password, String storedPassword) {
        return passwordEncoder.matches(password, storedPassword);
    }

    /**
     * Encodes the provided password.
     *
     * @param password the password provided by the user
     * @return the encoded password
     */
    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}