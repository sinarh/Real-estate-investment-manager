package com.example.realestatemanager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SearchProperties extends JPanel {
    private JTextArea textArea;

    public SearchProperties() {
        AppUi.preparePage(this);

        AppUi.addHeader(this, "Search Properties",
                () -> {
                    GUI.searchprop.setVisible(false);
                    GUI.ptymgt.setVisible(true);
                },
                () -> {
                    GUI.searchprop.setVisible(false);
                    GUI.login.setVisible(true);
                });

        JLabel title = AppUi.pageTitle(
                "Search Properties",
                "Filter your owned portfolio by location, type, age, price, or features.");
        title.setBounds(34, 100, 680, 70);
        add(title);

        AppUi.Card filterCard = new AppUi.Card();
        filterCard.setBounds(34, 190, 906, 170);
        add(filterCard);

        JComboBox<String> proptypes = combo(new String[]{"All", "House", "Townhouse", "Condo", "Apartment", "Suite"});
        JTextField location = field();
        JTextField year = field();
        JComboBox<String> price = combo(new String[]{"All", "$0 - $499,999", "$500,000 - $1,000,000", "$1,000,000+"});
        JComboBox<String> features = combo(new String[]{"All", "Pool", "Gym", "Balcony", "Patio/Backyard", "Driveway", "Garage"});

        addLabeled(filterCard, "Property Type", proptypes, 22, 24, 160);
        addLabeled(filterCard, "Country", location, 220, 24, 160);
        addLabeled(filterCard, "Year Built", year, 418, 24, 130);
        addLabeled(filterCard, "Price", price, 586, 24, 210);
        addLabeled(filterCard, "Features", features, 22, 92, 190);

        javax.swing.JButton search = AppUi.primaryButton("Search Portfolio");
        search.setBounds(682, 96, 170, 38);
        search.addActionListener((ActionEvent e) -> {
            try {
                Integer loggedInID = Login.getLoggedInID();
                if (loggedInID == null) {
                    throw new IllegalArgumentException("Please log in before searching properties.");
                }
                String propertyType = (String) proptypes.getSelectedItem();
                String country = location.getText().trim().isEmpty() ? "All" : location.getText().trim();
                int yearBuilt = FormValidator.optionalInt(year, "Year Built", 0);
                String priceRange = (String) price.getSelectedItem();
                String featureRange = (String) features.getSelectedItem();
                List<String> searchResults = DatabaseConnection.searchProperty(loggedInID, propertyType, country, yearBuilt, priceRange, featureRange);
                displaySearchResults(searchResults);
            } catch (IllegalArgumentException ex) {
                FormValidator.showError(SearchProperties.this, ex);
            }
        });
        filterCard.add(search);

        AppUi.Card resultsCard = new AppUi.Card();
        resultsCard.setBounds(34, 390, 906, 260);
        add(resultsCard);

        JLabel resultsTitle = new JLabel("Results");
        resultsTitle.setFont(AppUi.H2);
        resultsTitle.setForeground(AppUi.NAVY);
        resultsTitle.setBounds(20, 16, 180, 28);
        resultsCard.add(resultsTitle);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(AppUi.BODY);
        textArea.setForeground(AppUi.NAVY);
        textArea.setBackground(Color.WHITE);
        textArea.setPreferredSize(new Dimension(840, 180));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        scrollPane.setBounds(20, 55, 866, 185);
        resultsCard.add(scrollPane);

        setVisible(false);
    }

    private JComboBox<String> combo(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFocusable(false);
        comboBox.setFont(AppUi.BODY);
        return comboBox;
    }

    private JTextField field() {
        JTextField textField = new JTextField();
        textField.setFont(AppUi.BODY);
        return textField;
    }

    private void addLabeled(JPanel parent, String label, java.awt.Component input, int x, int y, int width) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(AppUi.LABEL);
        labelComponent.setForeground(AppUi.MUTED);
        labelComponent.setBounds(x, y, width, 18);
        parent.add(labelComponent);

        input.setBounds(x, y + 24, width, 34);
        parent.add(input);
    }

    private void displaySearchResults(List<String> propertyNames) {
        if (propertyNames.isEmpty()) {
            textArea.setText("No matching properties found.");
            return;
        }

        StringBuilder resultText = new StringBuilder();
        for (String propertyName : propertyNames) {
            resultText.append("- ").append(propertyName).append("\n");
        }
        textArea.setText(resultText.toString());
    }
}
