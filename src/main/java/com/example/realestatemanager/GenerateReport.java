package com.example.realestatemanager;

import java.awt.BorderLayout;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GenerateReport extends JPanel {
    private String selectedMonth = "1";
    private String selectedYear = "2024";
    private JTextArea textArea;
    private JPanel reportPage;
    private BarChartPanel chartPanel;

    public GenerateReport() {
        AppUi.preparePage(this);

        AppUi.addHeader(this, "Generate Report",
                () -> {
                    GUI.genreport.setVisible(false);
                    GUI.home.setVisible(true);
                },
                () -> {
                    GUI.genreport.setVisible(false);
                    GUI.login.setVisible(true);
                });

        JLabel title = AppUi.pageTitle(
                "Investment Reports",
                "Create summary and profit views for the selected month and year.");
        title.setBounds(34, 100, 680, 70);
        add(title);

        AppUi.Card controls = new AppUi.Card();
        controls.setBounds(34, 190, 906, 120);
        add(controls);

        JComboBox<String> reportTypes = combo(new String[]{"Profit Report", "Summary Report"});
        JComboBox<String> years = combo(new String[]{"2020", "2021", "2022", "2023", "2024", "2025", "2026"});
        years.setSelectedItem("2024");
        years.addActionListener(e -> selectedYear = (String) years.getSelectedItem());

        JComboBox<String> months = combo(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
        months.addActionListener(e -> selectedMonth = (String) months.getSelectedItem());

        addLabeled(controls, "Report Type", reportTypes, 22, 24, 190);
        addLabeled(controls, "Year", years, 245, 24, 120);
        addLabeled(controls, "Month", months, 398, 24, 120);

        javax.swing.JButton generate = AppUi.primaryButton("Generate");
        generate.setBounds(570, 50, 130, 38);
        generate.addActionListener(e -> {
            String selectedReportType = (String) reportTypes.getSelectedItem();
            if ("Profit Report".equals(selectedReportType)) {
                generateCountryProfitReport();
            } else {
                generateMonthlyYearlyReport(selectedMonth, selectedYear);
            }
        });
        controls.add(generate);

        javax.swing.JButton print = AppUi.primaryButton("Print");
        print.setBounds(725, 50, 130, 38);
        print.addActionListener(e -> printReport(reportPage));
        controls.add(print);

        reportPage = new AppUi.Card();
        reportPage.setLayout(null);
        reportPage.setBackground(java.awt.Color.WHITE);
        reportPage.setBounds(34, 340, 906, 310);
        add(reportPage);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(AppUi.BODY);
        textArea.setForeground(AppUi.NAVY);
        textArea.setMargin(new java.awt.Insets(18, 18, 18, 18));
        textArea.setPreferredSize(new Dimension(370, 240));
        textArea.setText("Choose report options and click Generate.");

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        scrollPane.setBounds(18, 18, 385, 274);
        reportPage.add(scrollPane);

        chartPanel = new BarChartPanel();
        chartPanel.setBounds(430, 18, 455, 274);
        reportPage.add(chartPanel);

        setVisible(false);
    }

    private JComboBox<String> combo(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFocusable(false);
        comboBox.setFont(AppUi.BODY);
        return comboBox;
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

    private void printReport(JPanel panel) {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Print Report");
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D graphics2D = (Graphics2D) graphics;
                graphics2D.translate(pageFormat.getImageableX() * 2, pageFormat.getImageableY() * 2);
                graphics2D.scale(0.5, 0.5);
                panel.paint(graphics2D);
                return Printable.PAGE_EXISTS;
            }
        });
        boolean returningResult = printerJob.printDialog();
        if (returningResult) {
            try {
                printerJob.print();
            } catch (PrinterException printerException) {
                JOptionPane.showMessageDialog(this, "Print Error: " + printerException.getMessage());
            }
        }
    }

    private void generateCountryProfitReport() {
        Integer loggedInID = Login.getLoggedInID();
        if (loggedInID == null) {
            JOptionPane.showMessageDialog(this, "Please log in before generating reports.", "Login required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<String> purchasedProperties = DatabaseConnection.getPropertyNamesByUserID(loggedInID);
        Map<String, Double> countryProfits = new HashMap<>();
        for (String property : purchasedProperties) {
            int buyingPrice = DatabaseConnection.getBuyingPrice(property);
            int marketValue = DatabaseConnection.getValue(property);
            int profit = marketValue - buyingPrice;
            String country = DatabaseConnection.getCountry(property);
            countryProfits.put(country, countryProfits.getOrDefault(country, 0.0) + profit);
        }
        List<Map.Entry<String, Double>> sortedCountryProfits = new ArrayList<>(countryProfits.entrySet());
        Collections.sort(sortedCountryProfits, (entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        StringBuilder report = new StringBuilder();
        report.append("Profit Report by Country\n\n");
        Map<String, Double> chartData = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : sortedCountryProfits) {
            report.append(entry.getKey()).append(": ").append(currency.format(entry.getValue())).append("\n");
            chartData.put(entry.getKey(), entry.getValue());
        }
        if (sortedCountryProfits.isEmpty()) {
            report.append("No owned properties found.");
        }
        textArea.setText(report.toString());
        chartPanel.setData("Profit by Country", chartData, currency);
    }

    private void generateMonthlyYearlyReport(String month, String year) {
        Integer loggedInID = Login.getLoggedInID();
        if (loggedInID == null) {
            JOptionPane.showMessageDialog(this, "Please log in before generating reports.", "Login required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        double monthlyNetProfit = DatabaseConnection.calculateMonthlyNetProfit(month, year, loggedInID);
        double monthlyTotalProfit = DatabaseConnection.calculateMonthlyTotalProfits(month, year, loggedInID);
        double yearlyNetProfit = DatabaseConnection.calculateYearlyNetProfit(year, loggedInID);
        double yearlyTotalProfit = DatabaseConnection.calculateYearlyTotalProfit(year, loggedInID);

        StringBuilder report = new StringBuilder();
        report.append("Summary Report for ").append(month).append("/").append(year).append("\n\n");
        report.append("Monthly Net Profit: ").append(currency.format(monthlyNetProfit)).append("\n");
        report.append("Monthly Total Profit: ").append(currency.format(monthlyTotalProfit)).append("\n\n");
        report.append("Yearly Net Profit: ").append(currency.format(yearlyNetProfit)).append("\n");
        report.append("Yearly Total Profit: ").append(currency.format(yearlyTotalProfit)).append("\n");

        textArea.setText(report.toString());

        Map<String, Double> monthlyData = new LinkedHashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthlyData.put(String.valueOf(i), DatabaseConnection.calculateMonthlyNetProfit(String.valueOf(i), year, loggedInID));
        }
        chartPanel.setData("Monthly Net Profit " + year, monthlyData, currency);
   }

    private static class BarChartPanel extends JPanel {
        private String title = "Chart";
        private Map<String, Double> data = new LinkedHashMap<>();
        private NumberFormat formatter = NumberFormat.getCurrencyInstance();

        BarChartPanel() {
            setBackground(Color.WHITE);
        }

        void setData(String title, Map<String, Double> data, NumberFormat formatter) {
            this.title = title;
            this.data = data;
            this.formatter = formatter;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(AppUi.NAVY);
            g2.setFont(AppUi.H2);
            g2.drawString(title, 12, 24);

            if (data.isEmpty()) {
                g2.setColor(AppUi.MUTED);
                g2.setFont(AppUi.BODY);
                g2.drawString("Generate a report to view chart data.", 12, 60);
                g2.dispose();
                return;
            }

            double maxMagnitude = 1;
            for (double value : data.values()) {
                maxMagnitude = Math.max(maxMagnitude, Math.abs(value));
            }

            int left = 42;
            int top = 48;
            int chartWidth = getWidth() - 70;
            int chartHeight = getHeight() - 96;
            int baseline = top + chartHeight / 2;

            g2.setColor(new Color(226, 232, 240));
            g2.setStroke(new BasicStroke(1));
            g2.drawLine(left, baseline, left + chartWidth, baseline);

            int count = data.size();
            int gap = Math.max(6, chartWidth / Math.max(count, 1) / 5);
            int barWidth = Math.max(10, (chartWidth - gap * (count + 1)) / Math.max(count, 1));
            int x = left + gap;

            g2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 10));
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                double value = entry.getValue();
                int barHeight = (int) Math.round((Math.abs(value) / maxMagnitude) * (chartHeight / 2.0 - 12));
                int y = value >= 0 ? baseline - barHeight : baseline;
                g2.setColor(value >= 0 ? AppUi.GREEN : new Color(220, 38, 38));
                g2.fillRoundRect(x, y, barWidth, barHeight, 8, 8);

                g2.setColor(AppUi.MUTED);
                g2.drawString(entry.getKey(), x, top + chartHeight + 18);
                x += barWidth + gap;
            }

            g2.setColor(AppUi.MUTED);
            g2.drawString(formatter.format(maxMagnitude), 6, top + 10);
            g2.drawString(formatter.format(-maxMagnitude), 6, top + chartHeight);
            g2.dispose();
        }
    }
}
