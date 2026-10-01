package com.example.realestatemanager;

import javax.swing.JFrame;

public class GUI {

    static JFrame frame = new JFrame("Real Estate Investment Manager");
    static Login login = new Login();
    static Home home = new Home();
    static Property_Management ptymgt = new Property_Management();
    static OwnedProperties ownpty = new OwnedProperties();
    static InterestedProperties intpty = new InterestedProperties();
    static Register register = new Register();
    static ResetPassword respass = new ResetPassword();
    static GenerateReport genreport = new GenerateReport();
    static SearchProperties searchprop = new SearchProperties();

    public static void showWindow() {
        UiTheme.install();
        frame.setBounds(100, 100, 1000, 750);
        frame.getContentPane().setBackground(UiTheme.BACKGROUND);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        frame.getContentPane().add(login);
        frame.getContentPane().add(home);
        frame.getContentPane().add(ptymgt);
        frame.getContentPane().add(ownpty);
        frame.getContentPane().add(intpty);
        frame.getContentPane().add(register);
        frame.getContentPane().add(respass);
        frame.getContentPane().add(genreport);
        frame.getContentPane().add(searchprop);

        UiTheme.apply(frame);
        frame.setVisible(true);

    }
    public static void main(String[] args) {
        try {
            showWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
