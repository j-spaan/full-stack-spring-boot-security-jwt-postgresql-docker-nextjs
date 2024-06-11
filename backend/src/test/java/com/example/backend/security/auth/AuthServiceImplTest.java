package com.example.backend.security.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
class AuthServiceImplTest {

    @Mock
    private Authentication authentication;

    @Test
    void testIsAuthenticationValid_whenAuthenticationIsNull() {
        assertFalse(this.isAuthenticationValid(null));
    }

    @Test
    void testIsAuthenticationValid_whenAuthenticationIsNotAuthenticated() {
        when(authentication.isAuthenticated()).thenReturn(false);
        assertFalse(this.isAuthenticationValid(authentication));
    }

    @Test
    void testIsAuthenticationValid_whenAuthenticationIsAuthenticated() {
        when(authentication.isAuthenticated()).thenReturn(true);
        assertTrue(this.isAuthenticationValid(authentication));
    }

    private boolean isAuthenticationValid(Authentication authentication) {
        return new AuthServiceImpl().isAuthenticationValid(authentication);
    }
}