package com.example.backend.security.password;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PasswordServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordServiceImpl passwordService;

    @Test
    void testIsPasswordValid() {
        String rawPassword = "rawPassword";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        boolean isValid = passwordService.isPasswordValid(rawPassword, encodedPassword);

        assertTrue(isValid);
    }

    @Test
    void testIsPasswordInvalid() {
        String rawPassword = "rawPassword";
        String encodedPassword = "wrongEncodedPassword";

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        boolean isValid = passwordService.isPasswordValid(rawPassword, encodedPassword);

        assertFalse(isValid);
    }

    @Test
    void testEncodePassword() {
        String rawPassword = "rawPassword";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        String result = passwordService.encodePassword(rawPassword);

        assertEquals(result, encodedPassword);
    }
}