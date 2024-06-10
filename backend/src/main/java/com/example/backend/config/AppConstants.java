package com.example.backend.config;

public final class AppConstants {

    private AppConstants() {
        throwUnsupportedOperationException();
    }

    public static final class Request {
        public static final String USERS = "/users";
        // add more constants as needed

        private Request() {
            throwUnsupportedOperationException();
        }
    }

    public static final class Table {
        public static final String USERS = "users";
        // add more constants as needed

        private Table() {
            throwUnsupportedOperationException();
        }
    }

    private static void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
