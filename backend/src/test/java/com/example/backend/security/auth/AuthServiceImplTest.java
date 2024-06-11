package com.example.backend.security.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceImplTest {

    @Mock
    private Authentication authentication;

    @Autowired
    private AuthServiceImpl authServiceImpl;

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