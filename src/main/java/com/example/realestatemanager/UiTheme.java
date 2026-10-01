package com.example.realestatemanager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public final class UiTheme {
    public static final Color BACKGROUND = new Color(244, 247, 251);
    public static final Color SURFACE = Color.WHITE;
    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color TEXT = new Color(17, 24, 39);
    public static final Color MUTED = new Color(75, 85, 99);
    public static final Color BORDER = new Color(203, 213, 225);
    public static final Font BASE = new Font("Segoe UI", Font.PLAIN, 15);
    public static final Font HEADING = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font TITLE = new Font("Segoe UI", Font.BOLD, 42);

    private UiTheme() {
    }

    public static void install() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
            // The app still works with the platform look and feel.
        }
        UIManager.put("control", BACKGROUND);
        UIManager.put("text", TEXT);
    }

    public static void apply(JFrame frame) {
        frame.getContentPane().setBackground(BACKGROUND);
        apply(frame.getContentPane());
    }

    public static void apply(Component component) {
        if (component instanceof JComponent jComponent
                && Boolean.TRUE.equals(jComponent.getClientProperty("customTheme"))) {
            return;
        }
        component.setFont(BASE);
        if (component instanceof JPanel panel) {
            panel.setBackground(BACKGROUND);
        }
        if (component instanceof JLabel label) {
            label.setForeground(TEXT);
            if (label.getFont() != null && label.getFont().getSize() >= 25) {
                label.setFont(HEADING);
            } else {
                label.setFont(BASE);
            }
        }
        if (component instanceof JButton button) {
            styleButton(button);
        }
        if (component instanceof JTextField field) {
            field.setFont(BASE);
            field.setForeground(TEXT);
            field.setBackground(SURFACE);
            field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDER, 1, true),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        }
        if (component instanceof JTextArea area) {
            area.setFont(BASE);
            area.setForeground(TEXT);
            area.setBackground(SURFACE);
            area.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDER, 1, true),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        }
        if (component instanceof JComponent jComponent) {
            jComponent.setOpaque(true);
        }
        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                apply(child);
            }
        }
    }

    private static void styleButton(JButton button) {
        button.setFont(BASE.deriveFont(Font.BOLD));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(30, 64, 175), 1, true),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)));
    }
}
