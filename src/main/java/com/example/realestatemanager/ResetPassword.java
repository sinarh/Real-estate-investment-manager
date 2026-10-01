package com.example.realestatemanager;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ResetPassword extends JPanel {
    private static JTextField enteruser;
    private static JTextField enteranswer;
    private static JPasswordField enternewpass;
    private final JLabel securityQuestion = new JLabel("");
    private final JLabel status = new JLabel("");
    private JLabel answerLabel;
    private JLabel newPasswordLabel;
    private final javax.swing.JButton next = AppUi.primaryButton("Next");
    private final javax.swing.JButton confirm = AppUi.primaryButton("Confirm Answer");
    private final javax.swing.JButton resetpass = AppUi.primaryButton("Reset Password");

    public ResetPassword() {
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
                "Reset password",
                "Verify your security answer before saving a new password.");
        title.setBounds(84, 118, 560, 70);
        add(title);

        AppUi.Card card = new AppUi.Card();
        card.setBounds(84, 210, 480, 350);
        add(card);

        enteruser = new JTextField();
        enteranswer = new JTextField();
        enternewpass = new JPasswordField();

        addLabeled(card, "Username", enteruser, 32, 32, 350);
        next.setBounds(32, 100, 160, 38);
        next.addActionListener(e -> showSecurityQuestion());
        card.add(next);

        securityQuestion.setFont(AppUi.BODY);
        securityQuestion.setForeground(AppUi.NAVY);
        securityQuestion.setBounds(32, 150, 400, 40);
        securityQuestion.setVisible(false);
        card.add(securityQuestion);

        answerLabel = addLabeled(card, "Security Answer", enteranswer, 32, 200, 350);
        answerLabel.setVisible(false);
        enteranswer.setVisible(false);
        confirm.setBounds(32, 268, 170, 38);
        confirm.addActionListener(e -> confirmAnswer());
        confirm.setVisible(false);
        card.add(confirm);

        newPasswordLabel = addLabeled(card, "New Password", enternewpass, 32, 200, 350);
        newPasswordLabel.setVisible(false);
        enternewpass.setVisible(false);
        resetpass.setBounds(32, 268, 170, 38);
        resetpass.addActionListener(e -> resetPassword());
        resetpass.setVisible(false);
        card.add(resetpass);

        status.setFont(AppUi.BODY);
        status.setForeground(AppUi.GREEN);
        status.setBounds(32, 200, 400, 28);
        status.setVisible(false);
        card.add(status);

        javax.swing.JButton back = linkButton("Back to login");
        back.setBounds(32, 310, 140, 28);
        back.addActionListener(e -> {
            resetView();
            GUI.respass.setVisible(false);
            GUI.login.setVisible(true);
        });
        card.add(back);

        setVisible(false);
    }

    private JLabel addLabeled(JPanel parent, String label, java.awt.Component input, int x, int y, int width) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(AppUi.LABEL);
        labelComponent.setForeground(AppUi.MUTED);
        labelComponent.setBounds(x, y, width, 18);
        parent.add(labelComponent);

        input.setFont(AppUi.BODY);
        input.setBounds(x, y + 24, width, 34);
        parent.add(input);
        return labelComponent;
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

    private void showSecurityQuestion() {
        String user = enteruser.getText().trim();
        if (!DatabaseConnection.usernameExists(user)) {
            JOptionPane.showMessageDialog(null, "No user found for the given username.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        securityQuestion.setText("<html>" + DatabaseConnection.retrieveSecurityQuestion(user) + "</html>");
        securityQuestion.setVisible(true);
        answerLabel.setVisible(true);
        enteranswer.setVisible(true);
        confirm.setVisible(true);
        next.setVisible(false);
    }

    private void confirmAnswer() {
        String answer = enteranswer.getText().trim();
        String user = enteruser.getText().trim();

        if (!DatabaseConnection.checkSecurityAnswer(user, answer)) {
            JOptionPane.showMessageDialog(null, "Incorrect security answer.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        securityQuestion.setVisible(false);
        answerLabel.setVisible(false);
        enteranswer.setVisible(false);
        confirm.setVisible(false);
        newPasswordLabel.setVisible(true);
        enternewpass.setVisible(true);
        resetpass.setVisible(true);
    }

    private void resetPassword() {
        String newPassword = new String(enternewpass.getPassword());
        String user = enteruser.getText().trim();

        boolean passwordUpdated = DatabaseConnection.updatePassword(user, newPassword);
        if (passwordUpdated) {
            status.setText("Password reset successful. You can return to login.");
            status.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to reset password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        enternewpass.setVisible(false);
        newPasswordLabel.setVisible(false);
        resetpass.setVisible(false);
    }

    private void resetView() {
        securityQuestion.setVisible(false);
        enteranswer.setVisible(false);
        enternewpass.setVisible(false);
        answerLabel.setVisible(false);
        newPasswordLabel.setVisible(false);
        status.setVisible(false);
        confirm.setVisible(false);
        resetpass.setVisible(false);
        next.setVisible(true);
    }
}
