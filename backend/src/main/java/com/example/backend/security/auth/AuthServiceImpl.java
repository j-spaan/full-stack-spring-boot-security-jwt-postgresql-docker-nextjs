package com.example.backend.security.auth;

import com.example.backend.http.HttpRequestService;
import com.example.backend.i18n.I18nService;
import com.example.backend.payload.exception.InvalidCredentialsException;
import com.example.backend.payload.request.AuthRegistrationRequest;
import com.example.backend.payload.request.AuthLoginRequest;
import com.example.backend.payload.response.AuthResponse;
import com.example.backend.security.jwt.JwtService;
import com.example.backend.security.password.PasswordService;
import com.example.backend.security.role.RoleService;
import com.example.backend.security.token.TokenService;
import com.example.backend.security.user.User;
import com.example.backend.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementation of the AuthService interface.
 * This service provides functionality to validate authentication objects.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link AuthenticationManager}: Spring Security's component that is responsible for validating the provided credentials during the authentication process.</li>
 *   <li>{@link HttpRequestService}: This class provides operations related to handling HTTP requests, such as retrieving the authorization header from the request.</li>
 *   <li>{@link I18nService}: This class provides operations related to internationalization and localization, such as retrieving localized messages.</li>
 *   <li>{@link JwtService}: This class is responsible for operations related to JSON Web Tokens (JWTs). This includes generating, validating, and parsing JWTs.</li>
 *   <li>{@link PasswordService}: This class provides operations related to handling passwords, such as encoding a plain text password.</li>
 *   <li>{@link TokenService}: This class provides operations related to handling tokens, such as validating tokens, saving tokens for a user, and revoking tokens.</li>
 *   <li>{@link UserService}: This class provides operations related to user management. Including finding users, creating users, updating user information, and deleting users.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Service}: This annotation is a specialized form of @Component, allows for implementation classes to be autodetected through classpath scanning.</li>
 *   <li>{@code @RequiredArgsConstructor}: This is a Lombok annotation to automatically generate a constructor with required arguments. Required arguments are final fields and fields with constraints such as @NonNull.</li>
 * </ul>
 *
 * @author Jeffrey Spaan
 * @since 2024-06-11
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final HttpRequestService httpRequestService;

    private final I18nService i18nService;

    private final JwtService jwtService;

    private final PasswordService passwordService;

    private final RoleService roleService;

    private final TokenService tokenService;

    private final UserService userService;

    @Value("${application.security.default-role}")
    private String defaultRole;

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

    /**
     * Registers a new user based on the provided registration request details.
     *
     * @param request the registration request containing the user's first name, last name, email, and password
     * @return an authentication response containing the JWT and the refresh token
     */
    @Override
    public AuthResponse register(AuthRegistrationRequest request) {
        // TODO Use mapper to model and model to mapper
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordService.encodePassword(request.getPassword()))
                .roles(Collections.singleton(
                        roleService.findRoleByName(defaultRole)))
                .build();
        User savedUser = userService.saveUser(user);
        String jwt = jwtService.generateToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());
        tokenService.saveTokenByUser(jwt, savedUser);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setDetail(i18nService.getMessage("auth.si.register.success", user.getFirstName()));
        authResponse.setInstance(null);
        authResponse.setAccessToken(jwt);
        authResponse.setRefreshToken(refreshToken);
        return authResponse;
    }

    /**
     * Authenticates a user based on the provided authentication request details.
     *
     * @param request the authentication request containing the user's email and password
     * @return an authentication response containing the JWT and the refresh token
     * @throws BadCredentialsException if the provided email and password are invalid
     */
    @Override
    public AuthResponse authenticate(AuthLoginRequest request) {
        final String emailOrUsername = request.getEmailOrUsername();
        User user = userService.findUserByEmailOrUsername(emailOrUsername, emailOrUsername);
        final String email = user.getEmail();
        authenticateByUsernameAndPassword(email, request.getPassword());
        String jwt = jwtService.generateToken(email);
        String refreshToken = jwtService.generateRefreshToken(email);
        tokenService.revokeAllTokensByUserId(user.getId());
        tokenService.saveTokenByUser(jwt, user);

        // TODO Use mapper to model and model to mapper
        AuthResponse authResponse = new AuthResponse();
        authResponse.setDetail(i18nService.getMessage("auth.si.authenticate.success", user.getFirstName()));
        authResponse.setInstance(httpRequestService.getRequestUri());
        authResponse.setAccessToken(jwt);
        authResponse.setRefreshToken(refreshToken);
        return authResponse;
    }

    /**
     * Refreshes the JWT token for the authenticated user.
     *
     * @return an authentication response containing the new JWT and the refresh token, or null if the refresh token is invalid
     */
    @Override
    public AuthResponse refreshToken() {
        final String refreshToken = httpRequestService.extractBearerToken();
        final String email = jwtService.extractSubject(refreshToken);
        User user = userService.findUserByEmail(email);
        if (!jwtService.isTokenValid(refreshToken, email)) {
            return null;
        }
        String jwt = jwtService.generateToken(email);
        tokenService.revokeAllTokensByUserId(user.getId());
        tokenService.saveTokenByUser(jwt, user);
        // TODO Use mapper to model and model to mapper

        AuthResponse authResponse = new AuthResponse();
        authResponse.setDetail(i18nService.getMessage("auth.si.refresh-token.success"));
        authResponse.setInstance(httpRequestService.getRequestUri());
        authResponse.setAccessToken(jwt);
        authResponse.setRefreshToken(refreshToken);
        return authResponse;
    }

    /**
     * Authenticates a user based on the provided email and password via the
     * Authentication Manager provided by Spring Security.
     *
     * @param email the email of the user to authenticate
     * @param password the password of the user to authenticate
     * @throws BadCredentialsException if the provided email and password are invalid
     */
    private void authenticateByUsernameAndPassword(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (AuthenticationException ex) {
            throw new InvalidCredentialsException("auth.si.invalid.credentials", new String[]{email});
        }
    }
}