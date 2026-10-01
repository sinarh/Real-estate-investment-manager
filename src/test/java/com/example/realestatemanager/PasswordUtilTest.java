package com.example.realestatemanager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PasswordUtilTest {
    @Test
    void verifiesHashedPasswords() {
        String hash = PasswordUtil.hash("password123".toCharArray());

        assertTrue(PasswordUtil.verify("password123".toCharArray(), hash));
        assertFalse(PasswordUtil.verify("wrong".toCharArray(), hash));
    }

    @Test
    void supportsLegacyPlaintextDuringMigration() {
        assertTrue(PasswordUtil.verify("old-password".toCharArray(), "old-password"));
        assertTrue(PasswordUtil.needsRehash("old-password"));
    }
}
