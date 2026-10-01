package com.example.realestatemanager;

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
import com.toedter.calendar.JCalendar;

public class CreateInterestedProperty extends JPanel{
    static JTextField propid;
    static JTextField type;
    static JTextField country;
    static JTextField province;
    static JTextField city;
    static JTextField year;
    static JTextArea proplink;
    static JTextField size;
    static JTextField bedrooms;
    static JTextField bathrooms;
    static JTextField features;
    static JTextField currentprice;
    static JTextField realname;
    static JTextField realnum;
    static JCalendar dateChooser = new JCalendar();

    static JCheckBox responseYesCheckBox = new JCheckBox("Yes");
    static JCheckBox responseNoCheckBox = new JCheckBox("No");
    static JButton createbtn = new JButton("Create Property Profile");
    static JButton savebtn = new JButton("Save Changes");

    static JButton delete = new JButton();

    public CreateInterestedProperty() {
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
        add(countrytxt);

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

        JLabel proplinktxt = new JLabel("Property Link: ");
        proplinktxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        proplinktxt.setBackground(SystemColor.activeCaption);
        proplinktxt.setBounds(50, 445, 135, 35);
        add(proplinktxt);

        proplink = new JTextArea();
        proplink.setBorder(new LineBorder(new Color(0, 0, 0)));
        proplink.setBackground(new Color(255, 255, 255));
        proplink.setFont(new Font("Times New Roman", Font.BOLD, 18));
        proplink.setBounds(225, 445, 200, 100);
        this.add(proplink);

        JLabel sizetxt = new JLabel("Size (sqft): ");
        sizetxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        sizetxt.setBackground(SystemColor.activeCaption);
        sizetxt.setBounds(50, 385, 100, 35);
        add(sizetxt);

        size = new JTextField();
        size.setBorder(new LineBorder(new Color(0, 0, 0)));
        size.setBackground(new Color(255, 255, 255));
        size.setFont(new Font("Times New Roman", Font.BOLD, 18));
        size.setBounds(225, 385, 200, 35);
        this.add(size);

        JLabel bedroomstxt = new JLabel("Bedrooms: ");
        bedroomstxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        bedroomstxt.setBackground(SystemColor.activeCaption);
        bedroomstxt.setBounds(540, 25, 100, 35);
        add(bedroomstxt);

        bedrooms = new JTextField();
        bedrooms.setBorder(new LineBorder(new Color(0, 0, 0)));
        bedrooms.setBackground(new Color(255, 255, 255));
        bedrooms.setFont(new Font("Times New Roman", Font.BOLD, 18));
        bedrooms.setBounds(750, 25, 200, 35);
        this.add(bedrooms);

        JLabel bathroomstxt = new JLabel("Bathrooms: ");
        bathroomstxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        bathroomstxt.setBackground(SystemColor.activeCaption);
        bathroomstxt.setBounds(540, 85, 125, 35);
        add(bathroomstxt);

        bathrooms = new JTextField();
        bathrooms.setBorder(new LineBorder(new Color(0, 0, 0)));
        bathrooms.setBackground(new Color(255, 255, 255));
        bathrooms.setFont(new Font("Times New Roman", Font.BOLD, 18));
        bathrooms.setBounds(750, 85, 200, 35);
        this.add(bathrooms);

        JLabel featuretxt = new JLabel("Features: ");
        featuretxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        featuretxt.setBackground(SystemColor.activeCaption);
        featuretxt.setBounds(540, 145, 90, 35);
        this.add(featuretxt);

        features = new JTextField();
        features.setBorder(new LineBorder(new Color(0, 0, 0)));
        features.setBackground(new Color(255, 255, 255));
        features.setFont(new Font("Times New Roman", Font.BOLD, 18));
        features.setBounds(750, 145, 200, 35);
        this.add(features);

        JLabel currentpricetxt = new JLabel("Current Price: ");
        currentpricetxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        currentpricetxt.setBackground(SystemColor.activeCaption);
        currentpricetxt.setBounds(540, 205, 135, 35);
        add(currentpricetxt);

        currentprice = new JTextField();
        currentprice.setBorder(new LineBorder(new Color(0, 0, 0)));
        currentprice.setBackground(new Color(255, 255, 255));
        currentprice.setFont(new Font("Times New Roman", Font.BOLD, 18));
        currentprice.setBounds(750, 205, 200, 35);
        this.add(currentprice);

        JLabel realnametxt = new JLabel("Realtor Name: ");
        realnametxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        realnametxt.setBackground(SystemColor.activeCaption);
        realnametxt.setBounds(540, 265, 135, 35);
        add(realnametxt);

        realname = new JTextField();
        realname.setBorder(new LineBorder(new Color(0, 0, 0)));
        realname.setBackground(new Color(255, 255, 255));
        realname.setFont(new Font("Times New Roman", Font.BOLD, 18));
        realname.setBounds(750, 265, 200, 35);
        this.add(realname);

        JLabel realnumtxt = new JLabel("Realtor Number: ");
        realnumtxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        realnumtxt.setBackground(SystemColor.activeCaption);
        realnumtxt.setBounds(540, 325, 150, 35);
        add(realnumtxt);

        realnum = new JTextField();
        realnum.setBorder(new LineBorder(new Color(0, 0, 0)));
        realnum.setBackground(new Color(255, 255, 255));
        realnum.setFont(new Font("Times New Roman", Font.BOLD, 18));
        realnum.setBounds(750, 325, 200, 35);
        this.add(realnum);

        JLabel contdatetxt = new JLabel("Contact Date: ");
        contdatetxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        contdatetxt.setBackground(SystemColor.activeCaption);
        contdatetxt.setBounds(540, 385, 150, 35);
        add(contdatetxt);

        dateChooser = new JCalendar();
        dateChooser.setBounds(750, 385, 225, 150);
        add(dateChooser);

        JLabel responsetxt = new JLabel("Response Received: ");
        responsetxt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        responsetxt.setBackground(SystemColor.activeCaption);
        responsetxt.setBounds(540, 545, 175, 35);
        add(responsetxt);

        responseYesCheckBox = new JCheckBox("Yes");
        responseYesCheckBox.setFocusable(false);
        responseYesCheckBox.setFont(new Font("Times New Roman", Font.BOLD, 20));
        responseYesCheckBox.setBackground(new Color(255, 255, 255));
        responseYesCheckBox.setBounds(750, 545, 100, 35);
        add(responseYesCheckBox);

        responseNoCheckBox = new JCheckBox("No");
        responseNoCheckBox.setFocusable(false);
        responseNoCheckBox.setFont(new Font("Times New Roman", Font.BOLD, 20));
        responseNoCheckBox.setBackground(new Color(255, 255, 255));
        responseNoCheckBox.setBounds(850, 545, 100, 35);
        add(responseNoCheckBox);

        responseYesCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (responseYesCheckBox.isSelected()) {
                    responseNoCheckBox.setSelected(false);
                }
            }
        });

        responseNoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (responseNoCheckBox.isSelected()) {
                    responseYesCheckBox.setSelected(false);
                }
            }
        });

        savebtn.setBounds(175, 608, 150, 38);
        savebtn.setFont(new Font("Times New Roman", Font.BOLD, 25));
        savebtn.setFocusTraversalKeysEnabled(false);
        savebtn.setFocusable(false);
        savebtn.setFocusPainted(false);
        savebtn.setVisible(false);
        add(savebtn);

        delete = new JButton("Delete");
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
                String propertyType;
                String propertyCountry;
                String propertyProvince;
                String propertyCity;
                int propertyYear;
                String propertyProplink;
                double propertySize;
                int propertyBedrooms;
                int propertyBathrooms;
                String propertyFeatures;
                double propertyCurrentPrice;
                String propertyRealtorName;
                String propertyRealtorNumber;
                java.sql.Date propertyContactDate;
                boolean propertyResponseReceived;
                int propertyUserId;
                try {
                    if (Login.getLoggedInID() == null) {
                        throw new IllegalArgumentException("Please log in before creating a property.");
                    }
                    Date selectedDate = dateChooser.getDate();
                    if (selectedDate == null) {
                        throw new IllegalArgumentException("Contact date is required.");
                    }
                    propertyName = FormValidator.required(propid, "Property ID/Name");
                    propertyType = FormValidator.required(type, "Property Type");
                    propertyCountry = FormValidator.required(country, "Country");
                    propertyProvince = FormValidator.required(province, "Province");
                    propertyCity = FormValidator.required(city, "City");
                    propertyYear = FormValidator.requiredInt(year, "Year Built");
                    propertyProplink = proplink.getText().trim();
                    propertySize = FormValidator.requiredDouble(size, "Size");
                    propertyBedrooms = FormValidator.requiredInt(bedrooms, "Bedrooms");
                    propertyBathrooms = FormValidator.requiredInt(bathrooms, "Bathrooms");
                    propertyFeatures = features.getText().trim();
                    propertyCurrentPrice = FormValidator.requiredDouble(currentprice, "Current Price");
                    propertyRealtorName = realname.getText().trim();
                    propertyRealtorNumber = realnum.getText().trim();
                    propertyContactDate = new java.sql.Date(selectedDate.getTime());
                    propertyResponseReceived = responseYesCheckBox.isSelected();
                    propertyUserId = Login.getLoggedInID();
                } catch (IllegalArgumentException ex) {
                    FormValidator.showError(CreateInterestedProperty.this, ex);
                    return;
                }

                    JRadioButton propertyprofile = new JRadioButton(propid.getText());

                    DatabaseConnection.insertInterestedProperty(propertyName, propertyType, propertyCountry, propertyProvince,
                            propertyCity, propertyYear, propertyProplink, propertySize, propertyBedrooms,
                            propertyBathrooms, propertyFeatures, propertyCurrentPrice, propertyRealtorName,
                            propertyRealtorNumber, propertyContactDate, propertyResponseReceived, propertyUserId);

                    String name = propid.getText();
                    createbtn.setVisible(true);
                    savebtn.setVisible(false);
                    CreatePropertyProfile.createinterested.setVisible(false);
                    CreatePropertyProfile.close();
                    propertyprofile.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            CreatePropertyProfile.showWindow();
                            CreatePropertyProfile.createinterested.setVisible(true);
                            propid.setText(propertyName);
                            type.setText(propertyType);
                            country.setText(propertyCountry);
                            province.setText(propertyProvince);
                            city.setText(propertyCity);
                            year.setText(String.valueOf(propertyYear));
                            proplink.setText(propertyProplink);
                            size.setText(String.valueOf(propertySize));
                            bedrooms.setText(String.valueOf(propertyBedrooms));
                            bathrooms.setText(String.valueOf(propertyBathrooms));
                            features.setText(propertyFeatures);
                            currentprice.setText(String.valueOf(propertyCurrentPrice));
                            realname.setText(propertyRealtorName);
                            realnum.setText(propertyRealtorNumber);
                            dateChooser.setDate(propertyContactDate);
                            responseYesCheckBox.setSelected(propertyResponseReceived);
                            responseNoCheckBox.setSelected(!propertyResponseReceived);
                            createbtn.setVisible(false);
                            savebtn.setVisible(true);
                            delete.setVisible(true);
                            delete.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    if (propertyprofile.isSelected()) {
                                        DatabaseConnection.deleteInterestedProperty(propid.getText());
                                        GUI.intpty.setVisible(false);
                                        GUI.intpty.setVisible(true);
                                        CreatePropertyProfile.createinterested.setVisible(false);
                                        CreatePropertyProfile.close();
                                        GUI.intpty.propertydisplay.remove(propertyprofile);
                                        GUI.intpty.group.remove(propertyprofile);
                                        delete.setVisible(false);
                                    }
                                }
                            });
                            savebtn.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
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
                                        // Set the parameters for the prepared statement
                                        pstmt.setString(1, propertyType);
                                        pstmt.setString(2, propertyCountry);
                                        pstmt.setString(3, propertyProvince);
                                        pstmt.setString(4, propertyCity);
                                        pstmt.setInt(5, propertyYear);
                                        pstmt.setString(6, propertyProplink);
                                        pstmt.setDouble(7, propertySize);
                                        pstmt.setInt(8, propertyBedrooms);
                                        pstmt.setInt(9, propertyBathrooms);
                                        pstmt.setString(10, propertyFeatures);
                                        pstmt.setDouble(11, propertyCurrentPrice);
                                        pstmt.setString(12, propertyRealtorName);
                                        pstmt.setString(13, propertyRealtorNumber);
                                        pstmt.setDate(14, propertyContactDate);
                                        pstmt.setBoolean(15, propertyResponseReceived);
                                        pstmt.setInt(16, Login.getLoggedInID());
                                        pstmt.setString(17, propertyName);
                                        int rowsAffected = pstmt.executeUpdate();
                                        if (rowsAffected > 0) {
                                            JOptionPane.showMessageDialog(null, "Updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Failed to update.", "Error", JOptionPane.ERROR_MESSAGE);
                                        }
                                    } catch (SQLException ex) {
                                        ex.printStackTrace();
                                    }

                                    GUI.intpty.setVisible(false);
                                    GUI.intpty.setVisible(true);
                                    CreatePropertyProfile.createinterested.setVisible(false);
                                    CreatePropertyProfile.close();
                                    delete.setVisible(false);
                                    savebtn.setSelected(false);
                                }
                            });
                        }
                    });
                    propertyprofile.setFont(new Font("Times New Roman", Font.BOLD, 25));
                    propertyprofile.setFocusTraversalKeysEnabled(false);
                    propertyprofile.setFocusable(false);
                    propertyprofile.setFocusPainted(false);
                    GUI.intpty.group.clearSelection();


                    GUI.intpty.displayUserPropertyNames();
                    propid.setText(null);
                    province.setText(null);
                    city.setText(null);
                    year.setText(null);
                    size.setText(null);
                    bedrooms.setText(null);
                    bathrooms.setText(null);
                    currentprice.setText(null);
                    GUI.intpty.setVisible(false);
                    GUI.intpty.setVisible(true);
                    createbtn.setVisible(true);
                    savebtn.setVisible(false);
                    CreatePropertyProfile.createinterested.setVisible(false);
                    CreatePropertyProfile.close();
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

    public static void clearForm() {
        propid.setText("");
        type.setText("");
        country.setText("");
        province.setText("");
        city.setText("");
        year.setText("");
        proplink.setText("");
        size.setText("");
        bedrooms.setText("");
        bathrooms.setText("");
        features.setText("");
        currentprice.setText("");
        realname.setText("");
        realnum.setText("");
        dateChooser.setDate(new Date());
        responseYesCheckBox.setSelected(false);
        responseNoCheckBox.setSelected(true);
    }

}


