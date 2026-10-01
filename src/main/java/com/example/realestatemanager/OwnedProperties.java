package com.example.realestatemanager;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class OwnedProperties extends JPanel {
    private static final long serialVersionUID = 1L;
    JPanel propertydisplay = new JPanel();
    ButtonGroup group = new ButtonGroup();

    public OwnedProperties() {
        AppUi.preparePage(this);

        AppUi.addHeader(this, "Owned Properties",
                () -> {
                    GUI.ownpty.setVisible(false);
                    GUI.ptymgt.setVisible(true);
                },
                () -> {
                    GUI.ownpty.setVisible(false);
                    GUI.login.setVisible(true);
                });

        JLabel title = AppUi.pageTitle(
                "Owned Properties",
                "Select a property to view or update its investment profile.");
        title.setBounds(34, 100, 600, 70);
        add(title);

        javax.swing.JButton create = AppUi.primaryButton("Create Property");
        create.setBounds(770, 116, 170, 38);
        create.addActionListener((ActionEvent e) -> {
            CreatePropertyProfile.createinterested.setVisible(false);
            CreatePropertyProfile.createowned.setVisible(true);
            CreateOwnedProperty.clearForm();
            CreateOwnedProperty.savebtn.setVisible(false);
            CreateOwnedProperty.delete.setVisible(false);
            CreateOwnedProperty.createbtn.setVisible(true);
            CreatePropertyProfile.showWindow();
        });
        add(create);

        AppUi.Card listCard = new AppUi.Card();
        listCard.setBounds(34, 190, 906, 460);
        add(listCard);

        propertydisplay.setBackground(Color.WHITE);
        propertydisplay.setLayout(new GridLayout(0, 1, 0, 6));

        JScrollPane scrollPane = new JScrollPane(propertydisplay);
        scrollPane.setBorder(null);
        scrollPane.setBounds(18, 18, 870, 424);
        listCard.add(scrollPane);

        displayUserPropertyNames();
        setVisible(false);
    }

    public void displayUserPropertyNames() {
        propertydisplay.removeAll();
        Integer loggedInID = Login.getLoggedInID();
        if (loggedInID == null) {
            propertydisplay.revalidate();
            propertydisplay.repaint();
            return;
        }

        List<String> propertyNames = DatabaseConnection.getPropertyNamesByUserID(loggedInID);
        for (String propertyName : propertyNames) {
            JRadioButton radioButton = AppUi.listItem(propertyName);
            radioButton.addActionListener(e -> openProperty(propertyName, radioButton));
            propertydisplay.add(radioButton);
            group.add(radioButton);
        }

        CreateOwnedProperty.savebtn.addActionListener(e -> updateSelectedProperty());
        propertydisplay.revalidate();
        propertydisplay.repaint();
    }

    private void openProperty(String propertyName, JRadioButton radioButton) {
        if (!radioButton.isSelected()) {
            return;
        }

        GUI.ownpty.group.clearSelection();
        CreatePropertyProfile.showWindow();
        CreatePropertyProfile.createowned.setVisible(true);
        CreateOwnedProperty.setPropertyName(propertyName);
        CreateOwnedProperty.propid.setText(propertyName);
        CreateOwnedProperty.type.setText(DatabaseConnection.getType(propertyName));
        CreateOwnedProperty.country.setText(DatabaseConnection.getCountry(propertyName));
        CreateOwnedProperty.buyingprice.setText(String.valueOf(DatabaseConnection.getBuyingPrice(propertyName)));
        CreateOwnedProperty.province.setText(DatabaseConnection.getProvince(propertyName));
        CreateOwnedProperty.city.setText(DatabaseConnection.getCity(propertyName));
        CreateOwnedProperty.year.setText(String.valueOf(DatabaseConnection.getYear(propertyName)));
        CreateOwnedProperty.size.setText(String.valueOf(DatabaseConnection.getSqft(propertyName)));
        CreateOwnedProperty.bedrooms.setText(String.valueOf(DatabaseConnection.getBedrooms(propertyName)));
        CreateOwnedProperty.bathrooms.setText(String.valueOf(DatabaseConnection.getBathrooms(propertyName)));
        CreateOwnedProperty.features.setText(DatabaseConnection.getFeatures(propertyName));
        CreateOwnedProperty.currentvalue.setText(String.valueOf(DatabaseConnection.getValue(propertyName)));
        CreateOwnedProperty.expenses.setText(String.valueOf(DatabaseConnection.getExpenses(propertyName)));
        CreateOwnedProperty.dateChooser.setDate(DatabaseConnection.getDate(propertyName));
        CreateOwnedProperty.createbtn.setVisible(false);
        CreateOwnedProperty.savebtn.setVisible(true);
        CreateOwnedProperty.delete.setVisible(true);

        CreateOwnedProperty.delete.addActionListener(e -> {
            DatabaseConnection.deleteProperty(propertyName);
            CreatePropertyProfile.createowned.setVisible(false);
            CreatePropertyProfile.close();
            CreateOwnedProperty.delete.setVisible(false);
            displayUserPropertyNames();
        });
    }

    private void updateSelectedProperty() {
        String sql = """
                UPDATE ownedproperties
                SET propertyType = ?,
                    country = ?,
                    province = ?,
                    city = ?,
                    yearbuilt = ?,
                    size_sqft = ?,
                    bedrooms = ?,
                    bathrooms = ?,
                    features = ?,
                    buyingprice = ?,
                    propertyValue = ?,
                    expenses = ?,
                    date = ?
                WHERE propertyname = ?
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, CreateOwnedProperty.type.getText());
            pstmt.setString(2, CreateOwnedProperty.country.getText());
            pstmt.setString(3, CreateOwnedProperty.province.getText());
            pstmt.setString(4, CreateOwnedProperty.city.getText());
            pstmt.setInt(5, Integer.parseInt(CreateOwnedProperty.year.getText()));
            pstmt.setDouble(6, Double.parseDouble(CreateOwnedProperty.size.getText()));
            pstmt.setInt(7, Integer.parseInt(CreateOwnedProperty.bedrooms.getText()));
            pstmt.setInt(8, Integer.parseInt(CreateOwnedProperty.bathrooms.getText()));
            pstmt.setString(9, CreateOwnedProperty.features.getText());
            pstmt.setDouble(10, Double.parseDouble(CreateOwnedProperty.buyingprice.getText()));
            pstmt.setDouble(11, Double.parseDouble(CreateOwnedProperty.currentvalue.getText()));
            pstmt.setInt(12, Integer.parseInt(CreateOwnedProperty.expenses.getText()));
            pstmt.setDate(13, new java.sql.Date(CreateOwnedProperty.dateChooser.getDate().getTime()));
            pstmt.setString(14, CreateOwnedProperty.getPropertyName());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
