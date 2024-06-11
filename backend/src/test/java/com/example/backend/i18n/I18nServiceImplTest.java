package com.example.backend.i18n;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class I18nServiceImplTest {

    @Mock
    private MessageSource messageSource;

    @Mock
    private HttpServletRequest request;

    @Autowired
    private I18nServiceImpl i18nServiceImpl;

    @Value("${spring.messages.logging-language}")
    private String loggingLanguage;

    private AutoCloseable mocks;

    @BeforeEach
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        i18nServiceImpl = new I18nServiceImpl(messageSource, request);
        i18nServiceImpl.setLoggingLanguage(loggingLanguage);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void testGetLogMessage() {
        // Arrange
        String code = "log.message";
        String expectedMessage = "This is a log message";
        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenReturn(expectedMessage);

        // Act
        String actualMessage = i18nServiceImpl.getLogMessage(code);

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetMessage_WithArgs() {
        // Arrange
        String code = "user.greeting";
        String[] args = {"John"};
        String expectedMessage = "Hello, John!";
        when(request.getLocale()).thenReturn(Locale.US);
        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenReturn(expectedMessage);

        // Act
        String actualMessage = i18nServiceImpl.getMessage(code, args);

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testGetMessage_WithoutArgs() {
        // Arrange
        String code = "welcome.message";
        String expectedMessage = "Welcome!";
        when(request.getLocale()).thenReturn(Locale.US);
        when(messageSource.getMessage(anyString(), any(), any(Locale.class)))
                .thenReturn(expectedMessage);

        // Act
        String actualMessage = i18nServiceImpl.getMessage(code);

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }
}