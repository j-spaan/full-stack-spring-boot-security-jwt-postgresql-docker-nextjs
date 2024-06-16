package com.example.backend.http;

public final class HttpConstants {

    HttpConstants() {
        throwUnsupportedOperationException();
    }

    public static final class Header {
        public static final String X_FORWARDED_FOR = "X-Forwarded-For";
        public static final String BEARER = "Bearer ";

        Header() {
            throwUnsupportedOperationException();
        }
    }

    public static final class Body {
        public static final String UTF_8 = "UTF-8";

        Body() {
            throwUnsupportedOperationException();
        }
    }

    private static void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}