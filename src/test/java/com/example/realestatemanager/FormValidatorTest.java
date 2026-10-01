package com.example.realestatemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

class FormValidatorTest {
    @Test
    void optionalIntUsesFallbackForBlankInput() {
        assertEquals(0, FormValidator.optionalInt(new JTextField(""), "Year Built", 0));
    }

    @Test
    void requiredDoubleRejectsInvalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> FormValidator.requiredDouble(new JTextField("abc"), "Buying Price"));
    }
}
