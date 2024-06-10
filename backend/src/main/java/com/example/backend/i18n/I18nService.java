package com.example.backend.i18n;

import org.springframework.lang.Nullable;

/**
 * @author Jeffrey Spaan
 * @since 2024-06-10
 * @see com.example.backend.i18n.I18nServiceImpl
 */
public interface I18nService {
    String getLogMessage(String code);
    String getMessage(String code, @Nullable String... args);
}