package com.example.backend.http;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class HttpConstantsTest {

    @Test
    void testHttpConstantsInstantiation() {
        assertThrows(UnsupportedOperationException.class, HttpConstants::new);
    }

    @Test
    void testHeaderInstantiation() {
        assertThrows(UnsupportedOperationException.class, HttpConstants.Header::new);
    }

    @Test
    void testBodyInstantiation() {
        assertThrows(UnsupportedOperationException.class, HttpConstants.Body::new);
    }

    @Test
    void testXForwardedForConstant() {
        assertEquals("X-Forwarded-For", HttpConstants.Header.X_FORWARDED_FOR);
    }

    @Test
    void testBearerForConstant() {
        assertEquals("Bearer ", HttpConstants.Header.BEARER);
    }

    @Test
    void testUtf8Constant() {
        assertEquals("UTF-8", HttpConstants.Body.UTF_8);
    }
}
