package com.example.backend.http;

import com.example.backend.i18n.I18nService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class HttpRequestServiceImplTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private I18nService i18nService;

    @InjectMocks
    private HttpRequestServiceImpl httpRequestServiceImpl;

    @Test
    void testGetUsername_WithPrincipal() {
        mockPrincipal("testUser");
        assertEquals("testUser", httpRequestServiceImpl.getUsername());
    }

    @Test
    void testGetUsername_WithoutPrincipal() {
        mockPrincipal(null);
        when(i18nService.getLogMessage("log.http.si.username.not.found")).thenReturn("Username not found error message");
        assertNull(httpRequestServiceImpl.getUsername());
    }

    @Test
    void testGetIp_WithXForwardedForHeader() {
        mockXForwardedForHeader("127.100.1.1, 127.100.1.2");
        assertEquals("127.100.1.1", httpRequestServiceImpl.getIp());
    }

    @Test
    void testGetIp_WithoutXForwardedForHeader() {
        mockXForwardedForHeader(null);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.100.1.1");
        assertEquals("127.100.1.1", httpRequestServiceImpl.getIp());
    }

    private void mockPrincipal(String username) {
        Principal principal = username != null ? () -> username : null;
        when(httpServletRequest.getUserPrincipal()).thenReturn(principal);
    }

    private void mockXForwardedForHeader(String headerValue) {
        when(httpServletRequest.getHeader(HttpConstants.Header.X_FORWARDED_FOR)).thenReturn(headerValue);
    }
}