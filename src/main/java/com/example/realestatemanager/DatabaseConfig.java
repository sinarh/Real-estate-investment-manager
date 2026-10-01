package com.example.realestatemanager;

public final class DatabaseConfig {
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/realestatemanager";
    private static final String DEFAULT_USER = "postgres";
    private static final String DEFAULT_PASSWORD = "";

    private DatabaseConfig() {
    }

    public static String url() {
        return read("REM_DB_URL", DEFAULT_URL);
    }

    public static String user() {
        return read("REM_DB_USER", DEFAULT_USER);
    }

    public static String password() {
        return read("REM_DB_PASSWORD", DEFAULT_PASSWORD);
    }

    private static String read(String key, String fallback) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value;
    }
}
