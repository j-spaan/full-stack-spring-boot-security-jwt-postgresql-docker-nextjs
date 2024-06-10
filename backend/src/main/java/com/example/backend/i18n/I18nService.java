package com.example.backend.i18n;

import org.springframework.lang.Nullable;

public interface I18nService {
    String getLogMessage(String code);
    String getMessage(String code, @Nullable String... args);
}