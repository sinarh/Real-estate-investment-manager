package com.example.realestatemanager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel {
    private static Integer loggedInID;
    private static String loggedInUsername;
    private static final long serialVersionUID = 1L;
    private static JTextField enteruser;
    private static JPasswordField enterpass;

    public Login() {
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
                "Welcome back",
                "Sign in to manage your portfolio, opportunities, and reports.");
        title.setBounds(282, 118, 520, 70);
        add(title);

        AppUi.Card card = new AppUi.Card();
        card.setBounds(282, 210, 420, 335);
        add(card);

        JLabel username = label("Username");
        username.setBounds(32, 34, 160, 20);
        card.add(username);

        enteruser = new JTextField();
        enteruser.setFont(AppUi.BODY);
        enteruser.setBounds(32, 62, 350, 36);
        card.add(enteruser);

        JLabel password = label("Password");
        password.setBounds(32, 118, 160, 20);
        card.add(password);

        enterpass = new JPasswordField();
        enterpass.setFont(AppUi.BODY);
        enterpass.setBounds(32, 146, 350, 36);
        card.add(enterpass);

        javax.swing.JButton loginButton = AppUi.primaryButton("Login");
        loginButton.setBounds(32, 210, 350, 38);
        loginButton.addActionListener(e -> login());
        card.add(loginButton);

        javax.swing.JButton resetButton = linkButton("Reset password");
        resetButton.setBounds(32, 260, 150, 28);
        resetButton.addActionListener(e -> {
            GUI.login.setVisible(false);
            GUI.respass.setVisible(true);
        });
        card.add(resetButton);

        javax.swing.JButton registerButton = linkButton("Create an account");
        registerButton.setBounds(225, 260, 160, 28);
        registerButton.addActionListener(e -> {
            GUI.login.setVisible(false);
            GUI.register.setVisible(true);
        });
        card.add(registerButton);

        setVisible(true);
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(AppUi.LABEL);
        label.setForeground(AppUi.MUTED);
        return label;
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

    private void login() {
        if (DatabaseConnection.authenticate(enteruser.getText().trim(), enterpass.getPassword())) {
            int id = DatabaseConnection.getUserId(enteruser.getText().trim());
            Login.setLoggedInID(id);
            Login.setLoggedInUsername(enteruser.getText().trim());
            DatabaseConnection.checkResponseAlert(id);
            GUI.ownpty.displayUserPropertyNames();
            GUI.intpty.displayUserPropertyNames();
            GUI.home.refreshDashboard();
            GUI.home.setVisible(true);
            GUI.login.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Integer getLoggedInID() {
        return loggedInID;
    }

    public static void setLoggedInID(Integer id) {
        loggedInID = id;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }
}
