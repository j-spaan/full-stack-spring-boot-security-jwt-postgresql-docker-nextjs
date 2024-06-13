package com.example.backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AppConstantsTest {

    @Test
    void testAppConstantsInstantiation() {
        assertThrows(UnsupportedOperationException.class, AppConstants::new);
    }

    @Test
    void testRequestInstantiation() {
        assertThrows(UnsupportedOperationException.class, AppConstants.Request::new);
    }

    @Test
    void testTableInstantiation() {
        assertThrows(UnsupportedOperationException.class, AppConstants.Table::new);
    }

    @Test
    void testRequestUsersConstant() {
        assertEquals("/users", AppConstants.Request.USERS);
    }

    @Test
    void testRequestAuthConstant() {
        assertEquals("/auth", AppConstants.Request.AUTH);
    }

    @Test
    void testTableUsersConstant() {
        assertEquals("users", AppConstants.Table.USERS);
    }

    @Test
    void testTableTokensConstant() {
        assertEquals("tokens", AppConstants.Table.TOKENS);
    }
}
