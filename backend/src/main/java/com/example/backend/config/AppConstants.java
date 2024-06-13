package com.example.backend.config;

public final class AppConstants {

    AppConstants() {
        throwUnsupportedOperationException();
    }

    public static final class Request {
        public static final String USERS = "/users";
        public static final String AUTH = "/auth";
        public static final String MANAGEMENT = "/management/**";
        // add more constants as needed

        Request() {
            throwUnsupportedOperationException();
        }
    }

    public static final class Table {
        public static final String USERS = "users";
        public static final String TOKENS = "tokens";
        // add more constants as needed

        Table() {
            throwUnsupportedOperationException();
        }
    }

    private static void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
