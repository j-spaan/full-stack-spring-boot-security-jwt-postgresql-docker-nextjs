package com.example.backend.security.auth;

import com.example.backend.http.HttpRequestService;
import com.example.backend.payload.exception.InvalidBearerTokenException;
import com.example.backend.payload.exception.ResourceNotFoundException;
import com.example.backend.payload.request.AuthLoginRequest;
import com.example.backend.security.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceImplTest {

    @Mock
    private Authentication authentication;

    @Mock
    private AuthLoginRequest authLoginRequest;

    @Mock
    private HttpRequestService httpRequestService;

    @Mock
    private UserService userService;

    @Autowired
    private AuthServiceImpl authServiceImpl;

    @Test
    void testAuthenticate_whenUserDoesNotExist() {
        when(authLoginRequest.getEmailOrUsername()).thenReturn("invalid@example.com");
        when(userService.findUserByEmailOrUsername("invalid@example.com", "invalid@example.com")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> authServiceImpl.authenticate(authLoginRequest));
    }

    @Test
    void testRefreshToken_whenBearerTokenIsInvalid() {
        when(httpRequestService.extractBearerToken()).thenThrow(new InvalidBearerTokenException("auth.si.invalid.bearer", null));

        assertThrows(InvalidBearerTokenException.class, () -> authServiceImpl.refreshToken());
    }

    @Test
    void testIsAuthenticationValid_whenAuthenticationIsNull() {
        assertFalse(authServiceImpl.isAuthenticationValid(null));
    }

    @Test
    void testIsAuthenticationValid_whenAuthenticationIsNotAuthenticated() {
        when(authentication.isAuthenticated()).thenReturn(false);
        assertFalse(authServiceImpl.isAuthenticationValid(authentication));
    }

    @Test
    void testIsAuthenticationValid_whenAuthenticationIsAuthenticated() {
        when(authentication.isAuthenticated()).thenReturn(true);
        assertTrue(authServiceImpl.isAuthenticationValid(authentication));
    }
}