package com.example.backend.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AuthService interface.
 * This service provides functionality to validate authentication objects.
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Service}: Marks this class as a Spring service.</li>
 * </ul>
 *
 * @author Jeffrey Spaan
 * @since 2024-06-11
 */
@Service
public class AuthServiceImpl implements AuthService {

    /**
     * Validates the given authentication object.
     *
     * @param authentication the authentication object to validate
     * @return true if the authentication object is not null and is authenticated,
     *         false otherwise
     */
    @Override
    public boolean isAuthenticationValid(Authentication authentication) {
        if (authentication != null) {
            return authentication.isAuthenticated();
        }
        return false;
    }
}