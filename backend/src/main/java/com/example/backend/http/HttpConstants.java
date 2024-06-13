package com.example.backend.http;

public final class HttpConstants {

    HttpConstants() {
        throwUnsupportedOperationException();
    }

    public static final class Header {
        public static final String X_FORWARDED_FOR = "X-Forwarded-For";

        Header() {
            throwUnsupportedOperationException();
        }
    }

    private static void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}