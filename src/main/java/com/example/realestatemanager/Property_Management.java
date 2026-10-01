package com.example.realestatemanager;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Property_Management extends JPanel {
    private static final long serialVersionUID = 1L;

    public Property_Management() {
        AppUi.preparePage(this);

        AppUi.addHeader(this, "Property Management",
                () -> {
                    GUI.ptymgt.setVisible(false);
                    GUI.home.setVisible(true);
                },
                () -> {
                    GUI.ptymgt.setVisible(false);
                    GUI.login.setVisible(true);
                });

        JLabel title = AppUi.pageTitle(
                "Manage your real estate pipeline",
                "Review current holdings, track prospective deals, or search the portfolio.");
        title.setBounds(34, 100, 620, 70);
        add(title);

        AppUi.ActionCard owned = new AppUi.ActionCard(
                "Owned Properties",
                "Review and update properties already in your portfolio.",
                AppUi.BLUE);
        owned.setBounds(34, 205, 430, 118);
        owned.setAction(() -> {
            GUI.ptymgt.setVisible(false);
            GUI.ownpty.displayUserPropertyNames();
            GUI.ownpty.setVisible(true);
        });
        add(owned);

        AppUi.ActionCard interested = new AppUi.ActionCard(
                "Interested Properties",
                "Track listings, realtor follow-ups, and possible purchases.",
                new java.awt.Color(124, 58, 237));
        interested.setBounds(512, 205, 430, 118);
        interested.setAction(() -> {
            GUI.ptymgt.setVisible(false);
            GUI.intpty.displayUserPropertyNames();
            GUI.intpty.setVisible(true);
        });
        add(interested);

        AppUi.ActionCard search = new AppUi.ActionCard(
                "Search Properties",
                "Filter your holdings by type, country, year, price, and features.",
                AppUi.GREEN);
        search.setBounds(34, 355, 430, 118);
        search.setAction(() -> {
            GUI.ptymgt.setVisible(false);
            GUI.searchprop.setVisible(true);
        });
        add(search);

        setVisible(false);
    }
}
