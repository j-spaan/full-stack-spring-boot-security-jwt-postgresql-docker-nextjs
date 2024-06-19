package com.example.backend.config;

public final class AppConstants {

    AppConstants() {
        throwUnsupportedOperationException();
    }

    public static final class Request {
        public static final String AUTH = "/auth";
        public static final String MANAGEMENT = "/management/**";
        public static final String ROLES = "/roles";
        public static final String USERS = "/users";
        // add more constants as needed (in alphabetical order)

        Request() {
            throwUnsupportedOperationException();
        }
    }

    public static final class Table {
        public static final String PERMISSIONS = "permissions";
        public static final String ROLES = "roles";
        public static final String TOKENS = "tokens";
        public static final String USERS = "users";
        // add more constants as needed (in alphabetical order)

        Table() {
            throwUnsupportedOperationException();
        }
    }

    private static void throwUnsupportedOperationException() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
