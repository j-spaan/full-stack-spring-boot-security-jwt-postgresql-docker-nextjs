package com.example.backend.config.handler;

import com.example.backend.http.HttpConstants;
import com.example.backend.i18n.I18nService;
import com.example.backend.payload.response.UnauthorizedResponse;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final Gson gson;

    private final I18nService i18nService;

    @Value("${spring.application.error-uri}")
    private String errorUri;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(HttpConstants.Body.UTF_8);

        // Create response body
        UnauthorizedResponse unauthorizedResponse = new UnauthorizedResponse();
        unauthorizedResponse.setType(errorUri);
        unauthorizedResponse.setDetail(i18nService.getMessage("auth.handler.incorrect.details"));
        unauthorizedResponse.setInstance(request.getRequestURI());

        String serializedResponse = gson.toJson(unauthorizedResponse);

        // Write response body into the response using the writer
        response.getWriter().write(serializedResponse);
    }
}