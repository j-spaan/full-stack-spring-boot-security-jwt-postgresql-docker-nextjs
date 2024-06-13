package com.example.backend.http;

/**
 * @author Jeffrey Spaan
 * @since 2024-06-10
 * @see com.example.backend.http.HttpRequestServiceImpl
 */
public interface HttpRequestService {
    String getUsername();
    String getIp();
    String getAuthorizationHeader();
    String getBearerToken();
}