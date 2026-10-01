package com.example.realestatemanager;

import javax.swing.JFrame;

public class CreatePropertyProfile {

    static JFrame frame = new JFrame();
    static CreateOwnedProperty createowned = new CreateOwnedProperty();
    static CreateInterestedProperty createinterested = new CreateInterestedProperty();

    public static void main(String[] args) {
        try {
            showWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showWindow() {
        frame.setTitle("Property Profile");
        frame.setBounds(250, 250, 1000, 750);
        frame.getContentPane().setBackground(AppUi.PAGE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);

        frame.getContentPane().add(createowned);
        frame.getContentPane().add(createinterested);

        UiTheme.apply(frame);
        frame.setVisible(true);

    }

    public static void close() {
        frame.setVisible(false);
    }

}
