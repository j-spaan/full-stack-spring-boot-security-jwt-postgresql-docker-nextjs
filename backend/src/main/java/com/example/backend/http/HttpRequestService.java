package com.example.backend.http;

/**
 * @author Jeffrey Spaan
 * @since 2024-06-10
 * @see com.example.backend.http.HttpRequestServiceImpl
 */
public interface HttpRequestService {
    String extractUsername();
    String extractIp();
    String getAuthorizationHeader();
    String getRequestUri();
    String extractBearerToken();
}