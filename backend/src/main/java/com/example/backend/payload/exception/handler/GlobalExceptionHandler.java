package com.example.backend.payload.exception.handler;

import com.example.backend.i18n.I18nService;
import com.example.backend.payload.exception.InvalidBearerTokenException;
import com.example.backend.payload.exception.InvalidCredentialsException;
import com.example.backend.payload.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

/**
 * Global exception handler for handling exceptions across the whole application.
 * Uses Spring's @ControllerAdvice to centralize exception handling logic.
 * It handles specific exceptions and provides a unified response structure.
 *
 * <p>Configured with the following properties:</p>
 * <ul>
 *   <li><code>spring.application.error-uri</code>: The configured error URI which is set within the application.yml file.</li>
 * </ul>
 *
 * <p>Dependencies:</p>
 * <ul>
 *   <li>{@link I18nService}: Service for internationalized messages.</li>
 * </ul>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @ControllerAdvice}: Provides global exception handling and advice for Spring MVC controllers.</li>
 *   <li>{@code @RequiredArgsConstructor}: Generates a constructor with required arguments.</li>
 * </ul>
 *
 * @author Jeffrey Spaan
 * @since 2024-06-10
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @Setter
    @Value("${spring.application.error-uri}")
    private String errorUri;

    private final I18nService i18nService;

    /**
     * Handles ResourceNotFoundException and returns a ProblemDetail object
     * with HTTP status 404 (Not Found) and a localized error message.
     *
     * @param ex the ResourceNotFoundException thrown when a resource is not found
     * @return ProblemDetail object containing the error details and HTTP status
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        return getProblemDetail(
                HttpStatus.NOT_FOUND,
                i18nService.getMessage(
                        ex.getMessage(),
                        ex.getArgs()
                )
        );
    }

    /**
     * Handles InvalidCredentialsException and returns a ProblemDetail object
     * with HTTP status 401 (Unauthorized) and a localized error message.
     *
     * @param ex the InvalidCredentialsException thrown when invalid credentials are provided
     * @return ProblemDetail object containing the error details and HTTP status
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    protected ProblemDetail handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return getProblemDetail(
                HttpStatus.UNAUTHORIZED,
                i18nService.getMessage(
                        ex.getMessage(),
                        ex.getArgs()
                )
        );
    }

    /**
     * Handles InvalidBearerTokenException and returns a ProblemDetail object
     * with HTTP status 401 (Unauthorized) and a localized error message.
     *
     * @param ex the InvalidBearerTokenException thrown when an invalid bearer token is provided
     * @return ProblemDetail object containing the error details and HTTP status
     */
    @ExceptionHandler(InvalidBearerTokenException.class)
    protected ProblemDetail handleInvalidBearerTokenException(InvalidBearerTokenException ex) {
        return getProblemDetail(
                HttpStatus.UNAUTHORIZED,
                i18nService.getMessage(
                        ex.getMessage(),
                        ex.getArgs()
                )
        );
    }

    /**
     * Constructs a ProblemDetail object with the specified HTTP status and detail message.
     * Sets the type of the problem detail to the configured error URI.
     *
     * @param httpStatus the HTTP status to set for the ProblemDetail
     * @param detail the detail message to set for the ProblemDetail
     * @return ProblemDetail object with the specified status and detail message
     */
    private ProblemDetail getProblemDetail(HttpStatus httpStatus, String detail) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                httpStatus,
                detail
        );
        pd.setType(URI.create(errorUri));
        return pd;
    }
}