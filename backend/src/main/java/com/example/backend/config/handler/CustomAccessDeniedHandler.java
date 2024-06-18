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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Gson gson;

    private final I18nService i18nService;

    @Value("${spring.application.error-uri}")
    private String errorUri;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding(HttpConstants.Body.UTF_8);

        // Create response body
        UnauthorizedResponse unauthorizedResponse = new UnauthorizedResponse();
        unauthorizedResponse.setType(errorUri);
        unauthorizedResponse.setDetail(i18nService.getMessage("acc.handler.forbidden"));
        unauthorizedResponse.setInstance(request.getRequestURI());

        String serializedResponse = gson.toJson(unauthorizedResponse);

        // Write response body into the response using the writer
        response.getWriter().write(serializedResponse);
    }
}