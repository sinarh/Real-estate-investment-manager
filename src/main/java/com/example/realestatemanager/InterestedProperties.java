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

public class InterestedProperties extends JPanel {
    private static final long serialVersionUID = 1L;
    JPanel propertydisplay = new JPanel();
    ButtonGroup group = new ButtonGroup();

    public InterestedProperties() {
        AppUi.preparePage(this);

        AppUi.addHeader(this, "Interested Properties",
                () -> {
                    GUI.intpty.setVisible(false);
                    GUI.ptymgt.setVisible(true);
                },
                () -> {
                    GUI.intpty.setVisible(false);
                    GUI.login.setVisible(true);
                });

        JLabel title = AppUi.pageTitle(
                "Interested Properties",
                "Track listings you are evaluating and realtor response status.");
        title.setBounds(34, 100, 600, 70);
        add(title);

        javax.swing.JButton create = AppUi.primaryButton("Create Property");
        create.setBounds(770, 116, 170, 38);
        create.addActionListener((ActionEvent e) -> {
            CreatePropertyProfile.createowned.setVisible(false);
            CreatePropertyProfile.createinterested.setVisible(true);
            CreateInterestedProperty.clearForm();
            CreateInterestedProperty.savebtn.setVisible(false);
            CreateInterestedProperty.delete.setVisible(false);
            CreateInterestedProperty.createbtn.setVisible(true);
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

        List<String> propertyNames = DatabaseConnection.getInterestedPropertyNamesByUserID(loggedInID);
        for (String propertyName : propertyNames) {
            JRadioButton radioButton = AppUi.listItem(propertyName);
            radioButton.addActionListener(e -> openProperty(propertyName, radioButton));
            propertydisplay.add(radioButton);
            group.add(radioButton);
        }

        CreateInterestedProperty.savebtn.addActionListener(e -> updateSelectedProperty());
        propertydisplay.revalidate();
        propertydisplay.repaint();
    }

    private void openProperty(String propertyName, JRadioButton radioButton) {
        if (!radioButton.isSelected()) {
            return;
        }

        GUI.intpty.group.clearSelection();
        CreatePropertyProfile.showWindow();
        CreatePropertyProfile.createinterested.setVisible(true);
        CreateInterestedProperty.savebtn.setVisible(true);
        CreateInterestedProperty.delete.setVisible(true);
        CreateInterestedProperty.createbtn.setVisible(false);
        CreateInterestedProperty.propid.setText(propertyName);
        CreateInterestedProperty.type.setText(DatabaseConnection.getIType(propertyName));
        CreateInterestedProperty.country.setText(DatabaseConnection.getICountry(propertyName));
        CreateInterestedProperty.province.setText(DatabaseConnection.getIProvince(propertyName));
        CreateInterestedProperty.city.setText(DatabaseConnection.getICity(propertyName));
        CreateInterestedProperty.year.setText(String.valueOf(DatabaseConnection.getIYear(propertyName)));
        CreateInterestedProperty.proplink.setText(String.valueOf(DatabaseConnection.getPropLink(propertyName)));
        CreateInterestedProperty.size.setText(String.valueOf(DatabaseConnection.getISize(propertyName)));
        CreateInterestedProperty.bedrooms.setText(String.valueOf(DatabaseConnection.getIBedrooms(propertyName)));
        CreateInterestedProperty.bathrooms.setText(String.valueOf(DatabaseConnection.getIBathrooms(propertyName)));
        CreateInterestedProperty.features.setText(DatabaseConnection.getIFeatures(propertyName));
        CreateInterestedProperty.currentprice.setText(String.valueOf(DatabaseConnection.getIBuyingPrice(propertyName)));
        CreateInterestedProperty.realname.setText(String.valueOf(DatabaseConnection.getRealtorName(propertyName)));
        CreateInterestedProperty.realnum.setText(String.valueOf(DatabaseConnection.getRealtorNumber(propertyName)));
        CreateInterestedProperty.dateChooser.setDate(DatabaseConnection.getContactDate(propertyName));
        CreateInterestedProperty.responseYesCheckBox.setSelected(DatabaseConnection.getResponseReceived(propertyName));
        CreateInterestedProperty.responseNoCheckBox.setSelected(!DatabaseConnection.getResponseReceived(propertyName));

        CreateInterestedProperty.delete.addActionListener(e -> {
            DatabaseConnection.deleteInterestedProperty(propertyName);
            CreatePropertyProfile.createinterested.setVisible(false);
            CreatePropertyProfile.close();
            CreateInterestedProperty.delete.setVisible(false);
            displayUserPropertyNames();
        });
    }

    private void updateSelectedProperty() {
        String sql = """
                UPDATE interestedproperties
                SET propertyType = ?,
                    country = ?,
                    province = ?,
                    city = ?,
                    yearbuilt = ?,
                    proplink = ?,
                    size_sqft = ?,
                    bedrooms = ?,
                    bathrooms = ?,
                    features = ?,
                    buyingprice = ?,
                    realtorname = ?,
                    realtornumber = ?,
                    contactdate = ?,
                    responsereceived = ?,
                    userid = ?
                WHERE propertyname = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, CreateInterestedProperty.type.getText());
            pstmt.setString(2, CreateInterestedProperty.country.getText());
            pstmt.setString(3, CreateInterestedProperty.province.getText());
            pstmt.setString(4, CreateInterestedProperty.city.getText());
            pstmt.setInt(5, Integer.parseInt(CreateInterestedProperty.year.getText()));
            pstmt.setString(6, CreateInterestedProperty.proplink.getText());
            pstmt.setDouble(7, Double.parseDouble(CreateInterestedProperty.size.getText()));
            pstmt.setInt(8, Integer.parseInt(CreateInterestedProperty.bedrooms.getText()));
            pstmt.setInt(9, Integer.parseInt(CreateInterestedProperty.bathrooms.getText()));
            pstmt.setString(10, CreateInterestedProperty.features.getText());
            pstmt.setDouble(11, Double.parseDouble(CreateInterestedProperty.currentprice.getText()));
            pstmt.setString(12, CreateInterestedProperty.realname.getText());
            pstmt.setString(13, CreateInterestedProperty.realnum.getText());
            pstmt.setDate(14, new java.sql.Date(CreateInterestedProperty.dateChooser.getDate().getTime()));
            pstmt.setBoolean(15, CreateInterestedProperty.responseYesCheckBox.isSelected());
            pstmt.setInt(16, Login.getLoggedInID());
            pstmt.setString(17, CreateInterestedProperty.propid.getText());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        CreatePropertyProfile.createinterested.setVisible(false);
        CreatePropertyProfile.close();
        CreateInterestedProperty.delete.setVisible(false);
        CreateInterestedProperty.savebtn.setSelected(false);
    }
}
