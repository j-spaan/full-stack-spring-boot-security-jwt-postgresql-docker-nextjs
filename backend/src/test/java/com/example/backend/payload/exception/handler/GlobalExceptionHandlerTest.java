package com.example.backend.payload.exception.handler;

import com.example.backend.i18n.I18nService;
import com.example.backend.payload.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
class GlobalExceptionHandlerTest {

    private static final String ERROR_URI = "https://example.com/error";
    private static final String ERROR_MESSAGE = "Resource not found";
    private static final String I18N_MESSAGE = "Resource not found (localized)";
    private static final int HTTP_STATUS_NOT_FOUND = 404;

    @Mock
    private I18nService i18nService;

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        try (AutoCloseable ignored = MockitoAnnotations.openMocks(this)) {
            globalExceptionHandler = new GlobalExceptionHandler(i18nService);
            globalExceptionHandler.setErrorUri(ERROR_URI);
        } catch (Exception ex) {
            log.error("Exception occurred while setting up the test GlobalExceptionHandlerTest with message: {}", ex.getMessage());
        }
    }

    @Test
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException(ERROR_MESSAGE, null);
        when(i18nService.getMessage(ERROR_MESSAGE, (String) null)).thenReturn(I18N_MESSAGE);

        ProblemDetail pd = globalExceptionHandler.handleResourceNotFoundException(ex);

        assertEquals(HTTP_STATUS_NOT_FOUND, pd.getStatus());
        assertEquals(I18N_MESSAGE, pd.getDetail());
        assertEquals(ERROR_URI, pd.getType().toString());
    }
}