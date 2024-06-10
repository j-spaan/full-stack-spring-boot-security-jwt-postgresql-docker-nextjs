package com.example.backend.http;

import com.example.backend.i18n.I18nService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * With each HTTP Servlet Request a diversity of headers are provided.
 * These headers contain data, such as the IP address of the client
 * or the principal (current logged-in user).
 * This class contains the methods to retrieve data from the headers.
 *
 * @author Jeffrey Spaan
 * @since 2024-06-10
 */

@Slf4j
@Service
public record HttpRequestServiceImpl(
        HttpServletRequest httpServletRequest,
        I18nService i18NService
) implements HttpRequestService {

    /**
     * Get the username of the principal using the Http Servlet Request.
     * @return username of the principal
     */
    @Override
    public String getUsername() {
        Principal httpUserPrincipal = httpServletRequest.getUserPrincipal();
        if (httpUserPrincipal == null) {
            log.error(i18NService.getLogMessage("log.http.si.username.not.found"));
            return null;
        }
        return httpUserPrincipal.getName();
    }

    /** Get the client IP using the Http Servlet Request.
     * @return client IP
     */
    @Override
    public String getIp() {
        final String xffHeader = httpServletRequest.getHeader(HttpConstants.Header.X_FORWARDED_FOR);
        if (xffHeader == null){
            return httpServletRequest.getRemoteAddr();
        }
        return xffHeader.split(",")[0];
    }
}