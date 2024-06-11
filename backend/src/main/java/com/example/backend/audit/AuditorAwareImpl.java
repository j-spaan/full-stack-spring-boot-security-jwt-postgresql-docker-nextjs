package com.example.backend.audit;

import com.example.backend.security.auth.AuthService;
import com.example.backend.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of AuditorAware based on Spring Security.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link AuthService}: Service for validating Authentication objects.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Component}: Marks this class as a Spring component.</li>
 * </ul>
 *
 * @author Jeffrey Spaan
 * @since 2024-06-11
 * @see <a href="https://docs.spring.io/spring-data/rest/reference/data-commons/auditing.html">AuditorAware Documentation</a>
 */
@Component
public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Autowired
    private AuthService authService;

    /**
     * This method retrieves the current authentication object from the security context,
     * validates it using the AuthService, and returns the user's UUID if the authentication is valid.
     *
     * @return an Optional containing the UUID of the current auditor, or an empty Optional if the authentication is not valid.
     */
    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authService.isAuthenticationValid(authentication)) {
            return Optional.empty();
        }

        User userPrincipal = (User) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
    }
}