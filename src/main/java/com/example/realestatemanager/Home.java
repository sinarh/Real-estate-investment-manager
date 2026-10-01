package com.example.realestatemanager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Home extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final Color PAGE = new Color(246, 248, 252);
    private static final Color NAVY = new Color(15, 23, 42);
    private static final Color BLUE = new Color(37, 99, 235);
    private static final Color GREEN = new Color(22, 163, 74);
    private static final Color MUTED = new Color(100, 116, 139);
    private static final Font TITLE = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font SECTION = new Font("Segoe UI", Font.BOLD, 17);
    private static final Font BODY = new Font("Segoe UI", Font.PLAIN, 13);

    private final JLabel ownedCountValue = new JLabel("0");
    private final JLabel watchlistCountValue = new JLabel("0");
    private final JLabel marketValueValue = new JLabel("$0");
    private final JLabel equityGainValue = new JLabel("$0");
    private final JLabel expensesValue = new JLabel("$0");
    private final JLabel heroTitle = new JLabel("Welcome back");

    public Home() {
        putClientProperty("customTheme", true);
        setBackground(PAGE);
        setBounds(0, 0, 985, 711);
        setLayout(null);

        addHeader();
        addHero();
        addMetrics();
        addActions();

        setVisible(false);
    }

    public void refreshDashboard() {
        Integer loggedInID = Login.getLoggedInID();
        if (loggedInID == null) {
            return;
        }

        PortfolioStats stats = DatabaseConnection.getPortfolioStats(loggedInID);
        String username = Login.getLoggedInUsername();
        heroTitle.setText("Welcome back" + (username == null || username.isBlank() ? "" : ", " + username));
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        ownedCountValue.setText(String.valueOf(stats.ownedCount()));
        watchlistCountValue.setText(String.valueOf(stats.interestedCount()));
        marketValueValue.setText(currency.format(stats.totalMarketValue()));
        equityGainValue.setText(currency.format(stats.equityGain()));
        expensesValue.setText(currency.format(stats.totalExpenses()));
    }

    private void addHeader() {
        JPanel header = new JPanel(null);
        header.setBackground(NAVY);
        header.setBounds(0, 0, 985, 72);
        add(header);

        JLabel brand = new JLabel("Real Estate Investment Manager");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 20));
        brand.setBounds(32, 18, 430, 34);
        header.add(brand);

        JButton logout = ghostButton("Logout");
        logout.setBounds(850, 20, 100, 32);
        logout.addActionListener((ActionEvent e) -> {
            GUI.home.setVisible(false);
            GUI.login.setVisible(true);
        });
        header.add(logout);
    }

    private void addHero() {
        RoundedPanel hero = new RoundedPanel(24, Color.WHITE);
        hero.setLayout(null);
        hero.setBounds(32, 100, 921, 230);
        add(hero);

        heroTitle.setForeground(NAVY);
        heroTitle.setFont(TITLE);
        heroTitle.setBounds(30, 74, 490, 48);
        hero.add(heroTitle);

        ImagePanel imagePanel = new ImagePanel("/images/portfolio-hero.png");
        imagePanel.setBounds(560, 18, 335, 194);
        hero.add(imagePanel);
    }

    private void addMetrics() {
        JLabel title = sectionLabel("Portfolio Snapshot");
        title.setBounds(34, 354, 240, 28);
        add(title);

        addMetric("Owned", ownedCountValue, 32, 392, BLUE);
        addMetric("Watching", watchlistCountValue, 217, 392, new Color(124, 58, 237));
        addMetric("Market Value", marketValueValue, 402, 392, new Color(8, 145, 178));
        addMetric("Equity Gain", equityGainValue, 587, 392, GREEN);
        addMetric("Expenses", expensesValue, 772, 392, new Color(220, 38, 38));
    }

    private void addActions() {
        JLabel title = sectionLabel("Next Steps");
        title.setBounds(34, 528, 180, 28);
        add(title);

        ActionCard manage = new ActionCard(
                "Property Management",
                "Add, edit, and review owned or interested properties.",
                BLUE);
        manage.setBounds(32, 568, 440, 96);
        manage.addActionListener(() -> {
            GUI.home.setVisible(false);
            GUI.ptymgt.setVisible(true);
        });
        add(manage);

        ActionCard reports = new ActionCard(
                "Generate Report",
                "View monthly, yearly, and country-level profit summaries.",
                GREEN);
        reports.setBounds(512, 568, 440, 96);
        reports.addActionListener(() -> {
            GUI.home.setVisible(false);
            GUI.genreport.setVisible(true);
        });
        add(reports);
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(NAVY);
        label.setFont(SECTION);
        return label;
    }

    private void addMetric(String label, JLabel value, int x, int y, Color accent) {
        RoundedPanel panel = new RoundedPanel(18, Color.WHITE);
        panel.setLayout(null);
        panel.setBounds(x, y, 160, 96);

        JPanel accentBar = new JPanel();
        accentBar.setBackground(accent);
        accentBar.setBounds(0, 0, 160, 5);
        panel.add(accentBar);

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelComponent.setForeground(MUTED);
        labelComponent.setBounds(16, 20, 125, 20);
        panel.add(labelComponent);

        value.setFont(new Font("Segoe UI", Font.BOLD, 16));
        value.setForeground(NAVY);
        value.setBounds(16, 48, 135, 28);
        panel.add(value);

        add(panel);
    }

    private JButton ghostButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 41, 59));
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return button;
    }

    private static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color fill;

        RoundedPanel(int radius, Color fill) {
            this.radius = radius;
            this.fill = fill;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fill);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.setColor(new Color(226, 232, 240));
            g2.setStroke(new BasicStroke(1));
            g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class ImagePanel extends JPanel {
        private final Image image;

        ImagePanel(String resourcePath) {
            ImageIcon icon = new ImageIcon(Home.class.getResource(resourcePath));
            image = icon.getImage();
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 18, 18));
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            g2.dispose();
        }
    }

    private static class ActionCard extends RoundedPanel {
        private Runnable action;

        ActionCard(String title, String copy, Color accent) {
            super(18, Color.WHITE);
            setLayout(null);
            setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

            JPanel icon = new JPanel();
            icon.setBackground(accent);
            icon.setBounds(22, 24, 48, 48);
            add(icon);

            JLabel titleLabel = new JLabel(title);
            titleLabel.setForeground(NAVY);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            titleLabel.setBounds(92, 18, 280, 28);
            add(titleLabel);

            JLabel copyLabel = new JLabel("<html>" + copy + "</html>");
            copyLabel.setForeground(MUTED);
            copyLabel.setFont(BODY);
            copyLabel.setBounds(92, 45, 300, 38);
            add(copyLabel);

            JLabel open = new JLabel("Open", SwingConstants.RIGHT);
            open.setForeground(accent);
            open.setFont(new Font("Segoe UI", Font.BOLD, 13));
            open.setBounds(365, 34, 48, 24);
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

        void addActionListener(Runnable action) {
            this.action = action;
        }
    }
}
