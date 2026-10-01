package com.example.realestatemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JPanel {
    private static final long serialVersionUID = 1L;
    private static JTextField enteruser;
    private static JPasswordField enterpass;
    private static JPasswordField confirmpass;
    private static JTextField secanswer;
    private String selectedQuestion;

    public Register() {
        AppUi.preparePage(this);

        JPanel header = new JPanel(null);
        header.setBackground(AppUi.NAVY);
        header.setBounds(0, 0, 985, 68);
        add(header);

        JLabel brand = new JLabel("Real Estate Investment Manager");
        brand.setForeground(java.awt.Color.WHITE);
        brand.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 21));
        brand.setBounds(32, 17, 430, 32);
        header.add(brand);

        JLabel title = AppUi.pageTitle(
                "Create your account",
                "Set up a local profile for tracking portfolio data.");
        title.setBounds(84, 100, 520, 70);
        add(title);

        AppUi.Card card = new AppUi.Card();
        card.setBounds(84, 190, 650, 440);
        add(card);

        enteruser = new JTextField();
        enterpass = new JPasswordField();
        confirmpass = new JPasswordField();
        secanswer = new JTextField();

        String[] questions = {
                "What is your maternal grandmother's maiden name?",
                "In what city or town was your first job?",
                "What is the name of your favorite childhood pet?",
                "What is the title of your favorite book?",
                "What is the name of the street you grew up on?"
        };
        selectedQuestion = questions[0];
        JComboBox<String> secquestion = new JComboBox<>(questions);
        secquestion.setFont(AppUi.BODY);
        secquestion.addActionListener(e -> selectedQuestion = (String) secquestion.getSelectedItem());

        addLabeled(card, "Username", enteruser, 32, 32, 250);
        addLabeled(card, "Password", enterpass, 32, 104, 250);
        addLabeled(card, "Confirm Password", confirmpass, 32, 176, 250);
        addLabeled(card, "Security Question", secquestion, 320, 32, 290);
        addLabeled(card, "Security Answer", secanswer, 320, 104, 250);

        javax.swing.JButton register = AppUi.primaryButton("Register Account");
        register.setBounds(320, 190, 190, 38);
        register.addActionListener(e -> registerAccount());
        card.add(register);

        javax.swing.JButton back = linkButton("Back to login");
        back.setBounds(32, 365, 140, 28);
        back.addActionListener(e -> {
            GUI.register.setVisible(false);
            GUI.login.setVisible(true);
        });
        card.add(back);

        setVisible(false);
    }

    private void addLabeled(JPanel parent, String label, java.awt.Component input, int x, int y, int width) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(AppUi.LABEL);
        labelComponent.setForeground(AppUi.MUTED);
        labelComponent.setBounds(x, y, width, 18);
        parent.add(labelComponent);

        input.setFont(AppUi.BODY);
        input.setBounds(x, y + 24, width, 34);
        parent.add(input);
    }

    private javax.swing.JButton linkButton(String text) {
        javax.swing.JButton button = new javax.swing.JButton(text);
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(AppUi.BLUE);
        button.setFont(AppUi.BUTTON);
        return button;
    }

    private void registerAccount() {
        String password = new String(enterpass.getPassword());
        String confirmedPassword = new String(confirmpass.getPassword());
        if (!password.equals(confirmedPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (enteruser.getText().trim().isEmpty() || password.isBlank() || secanswer.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username, password, and security answer are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password, security_question, security_answer) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, enteruser.getText().trim());
                pstmt.setString(2, PasswordUtil.hash(password.toCharArray()));
                pstmt.setString(3, selectedQuestion);
                pstmt.setString(4, PasswordUtil.hash(secanswer.getText().trim().toCharArray()));
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                GUI.register.setVisible(false);
                GUI.login.setVisible(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Registration failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
