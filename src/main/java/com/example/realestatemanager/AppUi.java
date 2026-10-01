package com.example.realestatemanager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public final class AppUi {
    public static final Color PAGE = new Color(246, 248, 252);
    public static final Color NAVY = new Color(15, 23, 42);
    public static final Color BLUE = new Color(37, 99, 235);
    public static final Color GREEN = new Color(22, 163, 74);
    public static final Color MUTED = new Color(100, 116, 139);
    public static final Color BORDER = new Color(226, 232, 240);
    public static final Font H1 = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font H2 = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font LABEL = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font BUTTON = new Font("Segoe UI", Font.BOLD, 13);

    private AppUi() {
    }

    public static void preparePage(JPanel panel) {
        panel.putClientProperty("customTheme", true);
        panel.setBackground(PAGE);
        panel.setBounds(0, 0, 985, 711);
        panel.setLayout(null);
    }

    public static void addHeader(JPanel page, String title, Runnable homeAction, Runnable logoutAction) {
        JPanel header = new JPanel(null);
        header.setBackground(NAVY);
        header.setBounds(0, 0, 985, 68);
        page.add(header);

        JLabel brand = new JLabel(title);
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 21));
        brand.setBounds(32, 17, 520, 32);
        header.add(brand);

        if (homeAction != null) {
            JButton home = ghostButton("Home");
            home.setBounds(730, 19, 86, 30);
            home.addActionListener(e -> homeAction.run());
            header.add(home);
        }

        JButton logout = ghostButton("Logout");
        logout.setBounds(836, 19, 100, 30);
        logout.addActionListener(e -> logoutAction.run());
        header.add(logout);
    }

    public static JLabel pageTitle(String text, String subtitle) {
        JLabel label = new JLabel("<html>" + text + "<br><span style='font-size:11px;font-weight:normal;color:#64748b;'>"
                + subtitle + "</span></html>");
        label.setForeground(NAVY);
        label.setFont(H1);
        return label;
    }

    public static JButton primaryButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(BLUE);
        button.setFont(BUTTON);
        button.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static JButton ghostButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 41, 59));
        button.setFont(BUTTON);
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static JRadioButton listItem(String text) {
        JRadioButton button = new JRadioButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(NAVY);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setFocusPainted(false);
        return button;
    }

    public static void modernizeEditorPanel(JPanel panel) {
        panel.putClientProperty("customTheme", true);
        panel.setBackground(PAGE);
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        modernizeChildren(panel);
    }

    private static void modernizeChildren(java.awt.Container container) {
        for (java.awt.Component child : container.getComponents()) {
            if (child instanceof JLabel label) {
                label.setFont(LABEL);
                label.setForeground(MUTED);
            }
            if (child instanceof JTextField field) {
                field.setFont(BODY);
                field.setForeground(NAVY);
                field.setBackground(Color.WHITE);
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        BorderFactory.createEmptyBorder(4, 8, 4, 8)));
            }
            if (child instanceof JTextArea area) {
                area.setFont(BODY);
                area.setForeground(NAVY);
                area.setBackground(Color.WHITE);
                area.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER, 1, true),
                        BorderFactory.createEmptyBorder(6, 8, 6, 8)));
            }
            if (child instanceof JComboBox<?> comboBox) {
                comboBox.setFont(BODY);
            }
            if (child instanceof JCheckBox checkBox) {
                checkBox.setFont(BODY);
                checkBox.setForeground(NAVY);
                checkBox.setBackground(PAGE);
                checkBox.setFocusPainted(false);
            }
            if (child instanceof JButton button) {
                button.setFont(BUTTON);
                button.setFocusPainted(false);
                button.setForeground(Color.WHITE);
                button.setBackground(BLUE);
                button.setBorder(BorderFactory.createEmptyBorder(7, 12, 7, 12));
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            if (child instanceof java.awt.Container nested) {
                modernizeChildren(nested);
            }
        }
    }

    public static class Card extends JPanel {
        private final int radius;
        private final Color fill;

        public Card() {
            this(18, Color.WHITE);
        }

        public Card(int radius, Color fill) {
            this.radius = radius;
            this.fill = fill;
            setOpaque(false);
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fill);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.setColor(BORDER);
            g2.setStroke(new BasicStroke(1));
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static class ActionCard extends Card {
        private Runnable action;

        public ActionCard(String title, String copy, Color accent) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JPanel icon = new JPanel();
            icon.setBackground(accent);
            icon.setBounds(20, 24, 42, 42);
            add(icon);

            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(H2);
            titleLabel.setForeground(NAVY);
            titleLabel.setBounds(82, 18, 250, 26);
            add(titleLabel);

            JLabel copyLabel = new JLabel("<html>" + copy + "</html>");
            copyLabel.setFont(BODY);
            copyLabel.setForeground(MUTED);
            copyLabel.setBounds(82, 45, 275, 40);
            add(copyLabel);

            JLabel open = new JLabel("Open", SwingConstants.RIGHT);
            open.setFont(BUTTON);
            open.setForeground(accent);
            open.setBounds(330, 36, 56, 22);
            add(open);

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (action != null) {
                        action.run();
                    }
                }
            });
        }

        public void setAction(Runnable action) {
            this.action = action;
        }
    }
}
