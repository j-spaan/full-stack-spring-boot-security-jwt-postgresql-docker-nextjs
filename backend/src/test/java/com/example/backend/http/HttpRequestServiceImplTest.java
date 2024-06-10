package com.example.backend.http;

import com.example.backend.i18n.I18nService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
class HttpRequestServiceImplTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private I18nService i18nService;

    @Test
    void testGetUsername_WithPrincipal() {
        mockPrincipal("testUser");
        assertEquals("testUser", this.getUsername());
    }

    @Test
    void testGetUsername_WithoutPrincipal() {
        mockPrincipal(null);
        when(i18nService.getLogMessage("log.http.si.username.not.found")).thenReturn("Username not found error message");
        assertNull(this.getUsername());
    }

    @Test
    void testGetIp_WithXForwardedForHeader() {
        mockXForwardedForHeader("127.100.1.1, 127.100.1.2");
        assertEquals("127.100.1.1", this.getIp());
    }

    @Test
    void testGetIp_WithoutXForwardedForHeader() {
        mockXForwardedForHeader(null);
        when(httpServletRequest.getRemoteAddr()).thenReturn("127.100.1.1");
        assertEquals("127.100.1.1", this.getIp());
    }

    private String getUsername() {
        return new HttpRequestServiceImpl(httpServletRequest, i18nService).getUsername();
    }

    private String getIp() {
        return new HttpRequestServiceImpl(httpServletRequest, i18nService).getIp();
    }

    private void mockPrincipal(String username) {
        Principal principal = username != null ? () -> username : null;
        when(httpServletRequest.getUserPrincipal()).thenReturn(principal);
    }

    private void mockXForwardedForHeader(String headerValue) {
        when(httpServletRequest.getHeader(HttpConstants.Header.X_FORWARDED_FOR)).thenReturn(headerValue);
    }
}