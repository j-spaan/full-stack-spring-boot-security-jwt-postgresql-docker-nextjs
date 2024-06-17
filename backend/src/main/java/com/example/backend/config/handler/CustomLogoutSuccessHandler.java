package com.example.backend.config.handler;

import com.example.backend.http.HttpConstants;
import com.example.backend.i18n.I18nService;
import com.example.backend.payload.response.OkResponse;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This custom logout success handler sends a JSON response with a success message.
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link I18nService}: This class provides operations related to internationalization and localization, such as retrieving messages in different languages.</li>
 *   <li>{@link Gson}: This class provides operations related to JSON serialization and deserialization.</li>
 * </ul>
 *
 * @author Jeffrey Spaan
 * @since 2024-06-15
 * @see org.springframework.security.web.authentication.logout.LogoutSuccessHandler
 */
@Component
public record CustomLogoutSuccessHandler(I18nService i18nService, Gson gson) implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding(HttpConstants.Body.UTF_8);

        // Create response body
        OkResponse okResponse = new OkResponse();
        okResponse.setDetail(i18nService.getMessage("logout.success"));
        okResponse.setInstance(request.getRequestURI());

        String serializedResponse = gson.toJson(okResponse);

        // Write response body into the response using the writer
        response.getWriter().write(serializedResponse);
    }
}