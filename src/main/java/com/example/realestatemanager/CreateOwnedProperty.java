package com.example.realestatemanager;

import com.toedter.calendar.JCalendar;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class CreateOwnedProperty extends JPanel {
    private static String propertyName;
    static JTextField propid = new JTextField();
    static JTextField province = new JTextField();
    static JTextField type = new JTextField();
    static JTextField country = new JTextField();
    static JTextField city = new JTextField();
    static JTextField year = new JTextField();
    static JTextField size = new JTextField();
    static JTextField bedrooms = new JTextField();
    static JTextField bathrooms = new JTextField();
    static JTextField features = new JTextField();
    static JTextField buyingprice = new JTextField();
    static JTextField currentvalue = new JTextField();
    static JTextField expenses = new JTextField();
    static JCalendar dateChooser = new JCalendar();
    static JButton createbtn = new JButton("Create Property Profile");
    static JButton savebtn = new JButton("Save Changes");
    static JButton delete = new JButton("Delete");

    public CreateOwnedProperty() {
        this.setForeground(new Color(0, 0, 0));
        this.setBounds(0, 0, 1000, 750);
        this.setLayout(null);

        JLabel propidtxt = new JLabel("Property ID/Name: ");
        propidtxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        propidtxt.setBackground(SystemColor.activeCaption);
        propidtxt.setBounds(50, 25, 175, 35);
        add(propidtxt);

        propid = new JTextField();
        propid.setBorder(new LineBorder(new Color(0, 0, 0)));
        propid.setBackground(new Color(255, 255, 255));
        propid.setFont(new Font("Times New Roman", Font.BOLD, 18));
        propid.setBounds(225, 25, 200, 35);
        this.add(propid);

        JLabel typetxt = new JLabel("Property Type: ");
        typetxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        typetxt.setBackground(SystemColor.activeCaption);
        typetxt.setBounds(50, 85, 140, 35);
        add(typetxt);

        type = new JTextField();
        type.setBorder(new LineBorder(new Color(0, 0, 0)));
        type.setBackground(new Color(255, 255, 255));
        type.setFont(new Font("Times New Roman", Font.BOLD, 18));
        type.setBounds(225, 85, 200, 35);
        this.add(type);

        JLabel countrytxt = new JLabel("Country: ");
        countrytxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        countrytxt.setBackground(SystemColor.activeCaption);
        countrytxt.setBounds(50, 145, 85, 35);
        this.add(countrytxt);

        country = new JTextField();
        country.setBorder(new LineBorder(new Color(0, 0, 0)));
        country.setBackground(new Color(255, 255, 255));
        country.setFont(new Font("Times New Roman", Font.BOLD, 18));
        country.setBounds(225, 145, 200, 35);
        this.add(country);

        JLabel provincetxt = new JLabel("Province: ");
        provincetxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        provincetxt.setBackground(SystemColor.activeCaption);
        provincetxt.setBounds(50, 205, 90, 35);
        add(provincetxt);

        province = new JTextField();
        province.setBorder(new LineBorder(new Color(0, 0, 0)));
        province.setBackground(new Color(255, 255, 255));
        province.setFont(new Font("Times New Roman", Font.BOLD, 18));
        province.setBounds(225, 205, 200, 35);
        this.add(province);

        JLabel citytxt = new JLabel("City: ");
        citytxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        citytxt.setBackground(SystemColor.activeCaption);
        citytxt.setBounds(50, 265, 90, 35);
        add(citytxt);

        city = new JTextField();
        city.setBorder(new LineBorder(new Color(0, 0, 0)));
        city.setBackground(new Color(255, 255, 255));
        city.setFont(new Font("Times New Roman", Font.BOLD, 18));
        city.setBounds(225, 265, 200, 35);
        this.add(city);

        JLabel yeartxt = new JLabel("Year Built: ");
        yeartxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        yeartxt.setBackground(SystemColor.activeCaption);
        yeartxt.setBounds(50, 325, 100, 35);
        add(yeartxt);

        year = new JTextField();
        year.setBorder(new LineBorder(new Color(0, 0, 0)));
        year.setBackground(new Color(255, 255, 255));
        year.setFont(new Font("Times New Roman", Font.BOLD, 18));
        year.setBounds(225, 325, 200, 35);
        this.add(year);

        JLabel sizetxt = new JLabel("Size (sqft): ");
        sizetxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        sizetxt.setBackground(SystemColor.activeCaption);
        sizetxt.setBounds(540, 25, 100, 35);
        add(sizetxt);

        size = new JTextField();
        size.setBorder(new LineBorder(new Color(0, 0, 0)));
        size.setBackground(new Color(255, 255, 255));
        size.setFont(new Font("Times New Roman", Font.BOLD, 18));
        size.setBounds(750, 25, 200, 35);
        this.add(size);

        JLabel bedroomstxt = new JLabel("Bedrooms: ");
        bedroomstxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        bedroomstxt.setBackground(SystemColor.activeCaption);
        bedroomstxt.setBounds(540, 85, 100, 35);
        add(bedroomstxt);

        bedrooms = new JTextField();
        bedrooms.setBorder(new LineBorder(new Color(0, 0, 0)));
        bedrooms.setBackground(new Color(255, 255, 255));
        bedrooms.setFont(new Font("Times New Roman", Font.BOLD, 18));
        bedrooms.setBounds(750, 85, 200, 35);
        this.add(bedrooms);

        JLabel bathroomstxt = new JLabel("Bathrooms: ");
        bathroomstxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        bathroomstxt.setBackground(SystemColor.activeCaption);
        bathroomstxt.setBounds(540, 145, 125, 35);
        add(bathroomstxt);

        bathrooms = new JTextField();
        bathrooms.setBorder(new LineBorder(new Color(0, 0, 0)));
        bathrooms.setBackground(new Color(255, 255, 255));
        bathrooms.setFont(new Font("Times New Roman", Font.BOLD, 18));
        bathrooms.setBounds(750, 145, 200, 35);
        this.add(bathrooms);

        JLabel featuretxt = new JLabel("Features: ");
        featuretxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        featuretxt.setBackground(SystemColor.activeCaption);
        featuretxt.setBounds(540, 205, 90, 35);
        this.add(featuretxt);

        features = new JTextField();
        features.setBorder(new LineBorder(new Color(0, 0, 0)));
        features.setBackground(new Color(255, 255, 255));
        features.setFont(new Font("Times New Roman", Font.BOLD, 18));
        features.setBounds(750, 205, 200, 35);
        this.add(features);

        JLabel buyingpricetxt = new JLabel("Buying Price: ");
        buyingpricetxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        buyingpricetxt.setBackground(SystemColor.activeCaption);
        buyingpricetxt.setBounds(540, 265, 125, 35);
        add(buyingpricetxt);

        buyingprice = new JTextField();
        buyingprice.setBorder(new LineBorder(new Color(0, 0, 0)));
        buyingprice.setBackground(new Color(255, 255, 255));
        buyingprice.setFont(new Font("Times New Roman", Font.BOLD, 18));
        buyingprice.setBounds(750, 265, 200, 35);
        this.add(buyingprice);

        JLabel currentvalueetxt = new JLabel("Current Market Value: ");
        currentvalueetxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        currentvalueetxt.setBackground(SystemColor.activeCaption);
        currentvalueetxt.setBounds(540, 325, 210, 35);
        add(currentvalueetxt);

        currentvalue = new JTextField();
        currentvalue.setBorder(new LineBorder(new Color(0, 0, 0)));
        currentvalue.setBackground(new Color(255, 255, 255));
        currentvalue.setFont(new Font("Times New Roman", Font.BOLD, 18));
        currentvalue.setBounds(750, 325, 200, 35);
        this.add(currentvalue);

        JLabel expensesTxt = new JLabel("Expenses: ");
        expensesTxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        expensesTxt.setBackground(SystemColor.activeCaption);
        expensesTxt.setBounds(540, 385, 100, 35);
        add(expensesTxt);

        expenses = new JTextField();
        expenses.setBorder(new LineBorder(new Color(0, 0, 0)));
        expenses.setBackground(new Color(255, 255, 255));
        expenses.setFont(new Font("Times New Roman", Font.BOLD, 18));
        expenses.setBounds(750, 385, 200, 35);
        this.add(expenses);

        JLabel contdatetxt = new JLabel("Contact Date: ");
        contdatetxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        contdatetxt.setBackground(SystemColor.activeCaption);
        contdatetxt.setBounds(540, 445, 150, 35);
        add(contdatetxt);

        dateChooser = new JCalendar();
        dateChooser.setBounds(750, 445, 225, 150);
        add(dateChooser);


        //save button properties.
        savebtn.setBounds(175, 608, 150, 38);
        savebtn.setFont(new Font("Times New Roman", Font.BOLD, 25));
        savebtn.setFocusTraversalKeysEnabled(false);
        savebtn.setFocusable(false);
        savebtn.setFocusPainted(false);
        savebtn.setVisible(false);
        add(savebtn);

        delete.setFont(new Font("Times New Roman", Font.BOLD, 25));
        delete.setFocusable(false);
        delete.setFocusTraversalKeysEnabled(false);
        delete.setFocusPainted(false);
        delete.setBounds(540, 608, 120, 38);
        delete.setVisible(false);
        add(delete);
        createbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String propertyName;
                int userId;
                String propertyType;
                String countryValue;
                String provinceValue;
                String cityValue;
                int yearBuilt;
                double sizeSqft;
                int bedroomsValue;
                int bathroomsValue;
                String featuresValue;
                double buyingPriceValue;
                double propertyValue;
                int expensesValue;
                java.sql.Date propertyContactDate;
                try {
                    if (Login.getLoggedInID() == null) {
                        throw new IllegalArgumentException("Please log in before creating a property.");
                    }
                    Date selectedDate = dateChooser.getDate();
                    if (selectedDate == null) {
                        throw new IllegalArgumentException("Purchase date is required.");
                    }
                    propertyName = FormValidator.required(propid, "Property ID/Name");
                    userId = Login.getLoggedInID();
                    propertyType = FormValidator.required(type, "Property Type");
                    countryValue = FormValidator.required(country, "Country");
                    provinceValue = FormValidator.required(province, "Province");
                    cityValue = FormValidator.required(city, "City");
                    yearBuilt = FormValidator.requiredInt(year, "Year Built");
                    sizeSqft = FormValidator.requiredDouble(size, "Size");
                    bedroomsValue = FormValidator.requiredInt(bedrooms, "Bedrooms");
                    bathroomsValue = FormValidator.requiredInt(bathrooms, "Bathrooms");
                    featuresValue = features.getText().trim();
                    buyingPriceValue = FormValidator.requiredDouble(buyingprice, "Buying Price");
                    propertyValue = FormValidator.requiredDouble(currentvalue, "Current Market Value");
                    expensesValue = FormValidator.requiredInt(expenses, "Expenses");
                    propertyContactDate = new java.sql.Date(selectedDate.getTime());
                } catch (IllegalArgumentException ex) {
                    FormValidator.showError(CreateOwnedProperty.this, ex);
                    return;
                }

                GUI.ownpty.group.clearSelection();
                CreatePropertyProfile.showWindow();

                boolean insertionSuccess = DatabaseConnection.insertOwnedProperty(
                        propertyName, userId, propertyType, countryValue, provinceValue, cityValue,
                        yearBuilt, sizeSqft, bedroomsValue, bathroomsValue, featuresValue, buyingPriceValue, propertyValue, expensesValue, propertyContactDate);

                if (insertionSuccess) {
                    JRadioButton propertyprofile = new JRadioButton(propid.getText());
                    propertyprofile.setFont(new Font("Times New Roman", Font.BOLD, 25));
                    CreatePropertyProfile.close();
                    propertyprofile.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            CreatePropertyProfile.showWindow();
                            CreatePropertyProfile.createowned.setVisible(true);
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
                            CreateOwnedProperty.dateChooser.setDate(propertyContactDate);
                            CreateOwnedProperty.createbtn.setVisible(false);
                            CreateOwnedProperty.savebtn.setVisible(true);
                            CreateOwnedProperty.delete.setVisible(true);
                            delete.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    if (propertyprofile.isSelected()) {
                                        DatabaseConnection.deleteProperty(propid.getText());
                                        GUI.ownpty.setVisible(false);
                                        GUI.ownpty.setVisible(true);
                                        CreatePropertyProfile.createowned.setVisible(false);
                                        CreatePropertyProfile.close();
                                        GUI.ownpty.propertydisplay.remove(propertyprofile);
                                        GUI.ownpty.group.remove(propertyprofile);
                                        delete.setVisible(false);
                                    }
                                }
                            });
                        }
                    });
                    CreateOwnedProperty.savebtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
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
                                pstmt.setDate(13, propertyContactDate);
                                pstmt.setString(14, propertyName);
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
                    });
                    GUI.ownpty.displayUserPropertyNames();

                    GUI.ownpty.group.clearSelection();
                    delete.setVisible(false);
                    savebtn.setVisible(false);
                    GUI.ownpty.setVisible(false);
                    GUI.ownpty.setVisible(true);
                    createbtn.setVisible(true);
                    CreatePropertyProfile.createowned.setVisible(false);
                    CreatePropertyProfile.close();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add the owned property.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        createbtn.setBounds(325, 608, 220, 38);
        createbtn.setFont(new Font("Times New Roman", Font.BOLD, 25));
        createbtn.setFocusTraversalKeysEnabled(false);
        createbtn.setFocusable(false);
        createbtn.setFocusPainted(false);
        add(createbtn);

        AppUi.modernizeEditorPanel(this);

        this.setVisible(false);
    }

    public static void setPropertyName(String name) {
        propertyName = name;
    }

    public static String getPropertyName() {
        return propertyName;
    }

    public static void clearForm() {
        propertyName = null;
        propid.setText("");
        type.setText("");
        country.setText("");
        province.setText("");
        city.setText("");
        year.setText("");
        size.setText("");
        bedrooms.setText("");
        bathrooms.setText("");
        features.setText("");
        buyingprice.setText("");
        currentvalue.setText("");
        expenses.setText("");
        dateChooser.setDate(new Date());
    }
}
