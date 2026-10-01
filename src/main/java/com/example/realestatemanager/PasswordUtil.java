package com.example.realestatemanager;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtil {
    private static final String PREFIX = "pbkdf2";
    private static final int ITERATIONS = 120_000;
    private static final int KEY_LENGTH = 256;
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordUtil() {
    }

    public static String hash(char[] value) {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        byte[] hash = pbkdf2(value, salt, ITERATIONS);
        return String.join("$",
                PREFIX,
                String.valueOf(ITERATIONS),
                Base64.getEncoder().encodeToString(salt),
                Base64.getEncoder().encodeToString(hash));
    }

    public static boolean verify(char[] candidate, String storedValue) {
        if (storedValue == null || storedValue.isBlank()) {
            return false;
        }
        if (!storedValue.startsWith(PREFIX + "$")) {
            return storedValue.equals(new String(candidate));
        }

        String[] parts = storedValue.split("\\$");
        if (parts.length != 4) {
            return false;
        }

        int iterations = Integer.parseInt(parts[1]);
        byte[] salt = Base64.getDecoder().decode(parts[2]);
        byte[] expected = Base64.getDecoder().decode(parts[3]);
        byte[] actual = pbkdf2(candidate, salt, iterations);
        return constantTimeEquals(expected, actual);
    }

    public static boolean needsRehash(String storedValue) {
        return storedValue == null || !storedValue.startsWith(PREFIX + "$");
    }

    private static byte[] pbkdf2(char[] value, byte[] salt, int iterations) {
        try {
            KeySpec spec = new PBEKeySpec(value, salt, iterations, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new IllegalStateException("Password hashing is unavailable.", ex);
        }
    }

    private static boolean constantTimeEquals(byte[] expected, byte[] actual) {
        if (expected.length != actual.length) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < expected.length; i++) {
            result |= expected[i] ^ actual[i];
        }
        return result == 0;
    }
}
