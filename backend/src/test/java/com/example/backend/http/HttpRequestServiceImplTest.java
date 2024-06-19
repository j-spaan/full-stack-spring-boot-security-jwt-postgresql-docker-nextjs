package com.example.backend.http;

import com.example.backend.i18n.I18nService;
import com.example.backend.payload.exception.InvalidBearerTokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
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
    void testExtractUsername_WithPrincipal() {
        mockPrincipal("testUser");
        assertEquals("testUser", httpRequestServiceImpl.extractUsername());
    }

    @Test
    void testExtractUsername_WithoutPrincipal() {
        mockPrincipal(null);
        when(i18nService.getLogMessage("log.http.si.username.not.found")).thenReturn("Username not found error message");
        assertNull(httpRequestServiceImpl.extractUsername());
    }

    @Test
    void testExtractIp_WithXForwardedForHeader() {
        mockXForwardedForHeader("127.100.1.1, 127.100.1.2");
        assertEquals("127.100.1.1", httpRequestServiceImpl.extractIp());
    }

    @Test
    void testExtractIp_WithoutXForwardedForHeader() {
        mockXForwardedForHeader(null);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.100.1.1");
        assertEquals("127.100.1.1", httpRequestServiceImpl.extractIp());
    }

    @Test
    void testGetAuthorizationHeader_WithAuthorizationHeader() {
        String bearerToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikpv";
        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn
                (bearerToken);
        assertEquals(bearerToken, httpRequestServiceImpl.getAuthorizationHeader());
    }

    @Test
    void testGetAuthorizationHeader_WithoutAuthorizationHeader() {
        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        assertNull(httpRequestServiceImpl.getAuthorizationHeader());
    }

    @Test
    void testGetRequestUri_WithUri() {
        when(httpServletRequest.getRequestURI()).thenReturn("/api/v1/test");
        assertEquals("/api/v1/test", httpRequestServiceImpl.getRequestUri());
    }

    @Test
    void testGetRequestUri_WithoutUri() {
        when(httpServletRequest.getRequestURI()).thenReturn("");
        assertEquals("", httpRequestServiceImpl.getRequestUri());
    }

    @Test
    void testExtractBearerToken_WithBearerToken() {
        String bearerToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikpv";
        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(bearerToken);
        assertEquals(bearerToken.substring(HttpConstants.Header.BEARER.length()).trim(), httpRequestServiceImpl.extractBearerToken());
    }

    @Test
    void testExtractBearerToken_WithoutBearerToken() {
        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        assertThrows(InvalidBearerTokenException.class, () -> httpRequestServiceImpl.extractBearerToken());
    }

    @Test
    void testExtractBearerToken_WithInvalidBearerToken() {
        String invalidBearerToken = "Invalid eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ikpv";
        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(invalidBearerToken);
        assertThrows(InvalidBearerTokenException.class, () -> httpRequestServiceImpl.extractBearerToken());
    }

    private void mockPrincipal(String username) {
        Principal principal = username != null ? () -> username : null;
        when(httpServletRequest.getUserPrincipal()).thenReturn(principal);
    }

    private void mockXForwardedForHeader(String headerValue) {
        when(httpServletRequest.getHeader(HttpConstants.Header.X_FORWARDED_FOR)).thenReturn(headerValue);
    }
}