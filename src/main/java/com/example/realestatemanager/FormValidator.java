package com.example.realestatemanager;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

public final class FormValidator {
    private FormValidator() {
    }

    public static String required(JTextComponent field, String label) {
        String value = field.getText().trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException(label + " is required.");
        }
        return value;
    }

    public static int optionalInt(JTextComponent field, String label, int fallback) {
        String value = field.getText().trim();
        if (value.isEmpty()) {
            return fallback;
        }
        return parseInt(value, label);
    }

    public static int requiredInt(JTextComponent field, String label) {
        return parseInt(required(field, label), label);
    }

    public static double requiredDouble(JTextComponent field, String label) {
        String value = required(field, label);
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(label + " must be a valid number.");
        }
    }

    public static void showError(Component parent, RuntimeException ex) {
        JOptionPane.showMessageDialog(parent, ex.getMessage(), "Check your entry", JOptionPane.WARNING_MESSAGE);
    }

    private static int parseInt(String value, String label) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(label + " must be a valid whole number.");
        }
    }
}
