package com.example.backend.http;

import com.example.backend.i18n.I18nService;
import com.example.backend.payload.exception.InvalidBearerTokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * With each HTTP Servlet Request a diversity of headers are provided.
 * These headers contain data, such as the IP address of the client
 * or the principal (current logged-in user).
 * This class contains the methods to retrieve data from the headers.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link I18nService}: Service for internationalized messages.</li>
 *   <li>{@link HttpServletRequest}: Represents an HTTP request and provides methods to access request parameters, headers, and attributes in a web application.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Slf4j}: Enables logging using SLF4J.</li>
 *   <li>{@code @Service}: Marks this class as a Spring service.</li>
 * </ul>
 *
 * @author Jeffrey Spaan
 * @since 2024-06-10
 */
@Slf4j
@Service
public record HttpRequestServiceImpl(
        HttpServletRequest httpServletRequest,
        I18nService i18nService
) implements HttpRequestService {

    /**
     * Extract the username of the principal using the Http Servlet Request.
     * @return username of the principal
     */
    @Override
    public String extractUsername() {
        Principal httpUserPrincipal = httpServletRequest.getUserPrincipal();
        if (httpUserPrincipal == null) {
            log.error(i18nService.getLogMessage("log.http.si.username.not.found"));
            return null;
        }
        return httpUserPrincipal.getName();
    }

    /** Extract the client IP from the X-Forwarded-For Header using the Http Servlet Request.
     * @return client IP
     */
    @Override
    public String extractIp() {
        final String xffHeader = httpServletRequest.getHeader(HttpConstants.Header.X_FORWARDED_FOR);
        if (xffHeader == null){
            return httpServletRequest.getRemoteAddr();
        }
        return xffHeader.split(",")[0];
    }

    /** Get the authorization header using the Http Servlet Request.
     * @return authorization header
     */
    @Override
    public String getAuthorizationHeader() {
        return httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    }

    /** Get the request URI using the Http Servlet Request.
     * @return request URI
     */
    @Override
    public String getRequestUri() {
        return httpServletRequest.getRequestURI();
    }

    /** Extract the bearer token from the authorization header using the Http Servlet Request.
     * @return bearer token
     */
    @Override
    public String extractBearerToken() {
        String authorizationHeader = this.getAuthorizationHeader();
        if (authorizationHeader == null || !authorizationHeader.startsWith(HttpConstants.Header.BEARER)) {
            throw new InvalidBearerTokenException(i18nService.getMessage("auth.si.invalid.bearer"), null);
        }
        return authorizationHeader.substring(HttpConstants.Header.BEARER.length()).trim();
    }
}