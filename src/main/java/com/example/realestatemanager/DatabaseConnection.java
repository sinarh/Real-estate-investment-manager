package com.example.realestatemanager;


import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DatabaseConfig.url(), DatabaseConfig.user(), DatabaseConfig.password());
    }

    public static boolean authenticate(String username, char[] password) {
        String sql = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                return false;
            }
            String storedPassword = rs.getString("password");
            boolean authenticated = PasswordUtil.verify(password, storedPassword);
            if (authenticated && PasswordUtil.needsRehash(storedPassword)) {
                updatePassword(username, new String(password));
            }
            return authenticated;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Arrays.fill(password, '\0');
        }
        return false;
    }
    public static String retrieveSecurityQuestion(String username) {
        String sql = "SELECT security_question FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("security_question");
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkSecurityAnswer(String username, String answer) {
        String sql = "SELECT security_answer FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedAnswer = rs.getString("security_answer");
                    return PasswordUtil.verify(answer.toCharArray(), storedAnswer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updatePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, PasswordUtil.hash(newPassword.toCharArray()));
            pstmt.setString(2, username);

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean insertOwnedProperty(
            String propertyName, int userId, String propertyType, String country, String province, String city, int yearBuilt,
            double sizeSqft, int bedrooms, int bathrooms, String features, double buyingPrice, double propertyValue, int expenses, Date date) {
        String sql = "INSERT INTO ownedproperties (" +
                "propertyname, userID, propertyType, country, province, " +
                "city, yearbuilt, size_sqft, bedrooms, bathrooms, features, buyingprice, propertyValue, expenses, date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            pstmt.setInt(2, userId);
            pstmt.setString(3, propertyType);
            pstmt.setString(4, country);
            pstmt.setString(5, province);
            pstmt.setString(6, city);
            pstmt.setInt(7, yearBuilt);
            pstmt.setDouble(8, sizeSqft);
            pstmt.setInt(9, bedrooms);
            pstmt.setInt(10, bathrooms);
            pstmt.setString(11, features);
            pstmt.setDouble(12, buyingPrice);
            pstmt.setDouble(13, propertyValue);
            pstmt.setInt(14, expenses);
            pstmt.setDate(15, date);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static int insertInterestedProperty(String propertyID, String propertyType, String country, String province,
                                               String city, int yearBuilt, String propertyLink, double sizeSqft, int bedrooms, int bathrooms,
                                               String features, double currentPrice, String realtorName, String realtorNumber, Date contactDate,
                                               boolean responseReceived, int userID) {
        int rowsAffected = 0;
        String sql = "INSERT INTO interestedproperties (propertyname, propertyType, country, province, city, yearbuilt, " +
                "proplink, size_sqft, bedrooms, bathrooms, features, buyingprice, realtorname, realtornumber, contactdate, " +
                "responsereceived, userid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyID);
            pstmt.setString(2, propertyType);
            pstmt.setString(3, country);
            pstmt.setString(4, province);
            pstmt.setString(5, city);
            pstmt.setInt(6, yearBuilt);
            pstmt.setString(7, propertyLink);
            pstmt.setDouble(8, sizeSqft);
            pstmt.setInt(9, bedrooms);
            pstmt.setInt(10, bathrooms);
            pstmt.setString(11, features);
            pstmt.setDouble(12, currentPrice);
            pstmt.setString(13, realtorName);
            pstmt.setString(14, realtorNumber);
            pstmt.setDate(15, contactDate);
            pstmt.setBoolean(16, responseReceived);
            pstmt.setInt(17, userID);
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public static boolean deleteInterestedProperty(String propertyName) {
        String sql = "DELETE FROM interestedproperties WHERE propertyname = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteProperty(String propertyName) {
        String sql = "DELETE FROM ownedproperties WHERE propertyname = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<String> getPropertyNamesByUserID(int userID) {
        List<String> propertyNames = new ArrayList<>();
            String GET_PROPERTY_NAMES_BY_USER_ID_QUERY =
                    "SELECT propertyname FROM ownedproperties WHERE userID = ?";

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(GET_PROPERTY_NAMES_BY_USER_ID_QUERY)) {
                pstmt.setInt(1, userID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String propertyName = rs.getString("propertyname");
                        propertyNames.add(propertyName);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return propertyNames;
    }

    public static List<String> getInterestedPropertyNamesByUserID(int userID) {
        List<String> propertyNames = new ArrayList<>();
        String GET_PROPERTY_NAMES_BY_USER_ID_QUERY =
                "SELECT propertyname FROM interestedproperties WHERE userID = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_PROPERTY_NAMES_BY_USER_ID_QUERY)) {
            pstmt.setInt(1, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String propertyName = rs.getString("propertyname");
                    propertyNames.add(propertyName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return propertyNames;
    }

    public static int getUserId(String username) {
            String sql = "SELECT id FROM users WHERE username = ?";
            int userId = -1;
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        userId = rs.getInt("id");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return userId;
    }

    public static PortfolioStats getPortfolioStats(int userId) {
        String ownedSql = """
                SELECT COUNT(*) AS owned_count,
                       COALESCE(SUM(propertyvalue), 0) AS total_value,
                       COALESCE(SUM(propertyvalue - buyingprice), 0) AS equity_gain,
                       COALESCE(SUM(expenses), 0) AS total_expenses
                FROM ownedproperties
                WHERE userid = ?
                """;
        String interestedSql = "SELECT COUNT(*) AS interested_count FROM interestedproperties WHERE userid = ?";

        try (Connection conn = getConnection();
             PreparedStatement ownedStatement = conn.prepareStatement(ownedSql);
             PreparedStatement interestedStatement = conn.prepareStatement(interestedSql)) {
            ownedStatement.setInt(1, userId);
            interestedStatement.setInt(1, userId);

            int ownedCount = 0;
            int interestedCount = 0;
            double totalValue = 0;
            double equityGain = 0;
            double totalExpenses = 0;

            try (ResultSet rs = ownedStatement.executeQuery()) {
                if (rs.next()) {
                    ownedCount = rs.getInt("owned_count");
                    totalValue = rs.getDouble("total_value");
                    equityGain = rs.getDouble("equity_gain");
                    totalExpenses = rs.getDouble("total_expenses");
                }
            }
            try (ResultSet rs = interestedStatement.executeQuery()) {
                if (rs.next()) {
                    interestedCount = rs.getInt("interested_count");
                }
            }
            return new PortfolioStats(ownedCount, interestedCount, totalValue, equityGain, totalExpenses);
        } catch (SQLException e) {
            e.printStackTrace();
            return new PortfolioStats(0, 0, 0, 0, 0);
        }
    }
    public static String getICity(String propertyName) {
        String sql = "SELECT city FROM interestedproperties WHERE propertyname = ?";
        String city = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    city = rs.getString("city");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    public static String getIType(String propertyName) {
        String sql = "SELECT propertytype FROM interestedproperties WHERE propertyname = ?";
        String type = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    type = rs.getString("propertytype");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    public static String getICountry(String propertyName) {
        String sql = "SELECT country FROM interestedproperties WHERE propertyname = ?";
        String country = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getString("country");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static String getIProvince(String propertyName) {
        String sql = "SELECT province FROM interestedproperties WHERE propertyname = ?";
        String country = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getString("province");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getIYear(String propertyName) {
        String sql = "SELECT yearbuilt FROM interestedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("yearbuilt");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getISqft(String propertyName) {
        String sql = "SELECT size_sqft FROM interestedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("size_sqft");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getIBedrooms(String propertyName) {
        String sql = "SELECT bedrooms FROM interestedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("bedrooms");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getIBathrooms(String propertyName) {
        String sql = "SELECT bathrooms FROM interestedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("bathrooms");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static String getIFeatures(String propertyName) {
        String sql = "SELECT features FROM interestedproperties WHERE propertyname = ?";
        String country = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getString("features");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getIBuyingPrice(String propertyName) {
        String sql = "SELECT buyingprice FROM interestedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("buyingprice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static String getPropLink(String propertyName) {
        String sql = "SELECT proplink FROM interestedproperties WHERE propertyname = ?";
        String country = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getString("proplink");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getISize(String propertyName) {
        String sql = "SELECT size_sqft FROM interestedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("size_sqft");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static String getRealtorName(String propertyName) {
        String sql = "SELECT realtorname FROM interestedproperties WHERE propertyname = ?";
        String country = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getString("realtorname");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static long getRealtorNumber(String propertyName) {
        String sql = "SELECT realtornumber FROM interestedproperties WHERE propertyname = ?";
        long country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getLong("realtornumber");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }
    public static Date getContactDate(String propertyName) {
        String sql = "SELECT contactdate FROM interestedproperties WHERE propertyname = ?";
        Date country = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getDate("contactdate");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static boolean getResponseReceived(String propertyName) {
        String sql = "SELECT responsereceived FROM interestedproperties WHERE propertyname = ?";
        boolean country = false;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getBoolean("responsereceived");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static String getCity(String propertyName) {
        String sql = "SELECT city FROM ownedproperties WHERE propertyname = ?";
        String city = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    city = rs.getString("city");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    public static String getType(String propertyName) {
        String sql = "SELECT propertytype FROM ownedproperties WHERE propertyname = ?";
        String type = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    type = rs.getString("propertytype");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    public static String getCountry(String propertyName) {
        String sql = "SELECT country FROM ownedproperties WHERE propertyname = ?";
        String country = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getString("country");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static String getProvince(String propertyName) {
        String sql = "SELECT province FROM ownedproperties WHERE propertyname = ?";
        String country = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getString("province");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getYear(String propertyName) {
        String sql = "SELECT yearbuilt FROM ownedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("yearbuilt");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getSqft(String propertyName) {
        String sql = "SELECT size_sqft FROM ownedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("size_sqft");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getBedrooms(String propertyName) {
        String sql = "SELECT bedrooms FROM ownedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("bedrooms");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getBathrooms(String propertyName) {
        String sql = "SELECT bathrooms FROM ownedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("bathrooms");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static String getFeatures(String propertyName) {
        String sql = "SELECT features FROM ownedproperties WHERE propertyname = ?";
        String country = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getString("features");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getBuyingPrice(String propertyName) {
        String sql = "SELECT buyingprice FROM ownedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("buyingprice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static int getValue(String propertyName) {
        String sql = "SELECT propertyvalue FROM ownedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("propertyvalue");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }
    public static int getExpenses(String propertyName) {
        String sql = "SELECT expenses FROM ownedproperties WHERE propertyname = ?";
        int country = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getInt("expenses");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static Date getDate(String propertyName) {
        String sql = "SELECT date FROM ownedproperties WHERE propertyname = ?";
        Date country = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, propertyName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    country = rs.getDate("date");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    public static double calculateMonthlyNetProfit(String month, String year, int userId) {
        double netProfit = 0.0;

        String sql = "SELECT SUM(propertyvalue - buyingprice - expenses) AS net_profit " +
                "FROM ownedproperties " +
                "WHERE EXTRACT(MONTH FROM date) = ? " +
                "AND EXTRACT(YEAR FROM date) = ? " +
                "AND userid = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(month));
            statement.setInt(2, Integer.parseInt(year));
            statement.setInt(3, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                netProfit = resultSet.getDouble("net_profit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return netProfit;
    }

    public static double calculateMonthlyTotalProfits(String month, String year, int userId) {
        double totalProfits = 0.0;
        String query = "SELECT SUM(propertyvalue - buyingprice) AS total_profit " +
                "FROM ownedproperties " +
                "WHERE EXTRACT(MONTH FROM date) = ? " +
                "AND EXTRACT(YEAR FROM date) = ? " +
                "AND userid = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(month));
            statement.setInt(2, Integer.parseInt(year));
            statement.setInt(3, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalProfits = resultSet.getDouble("total_profit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalProfits;
    }

    public static double calculateYearlyNetProfit(String year, int userId) {
        double yearlyNetProfit = 0.0;
        String query = "SELECT SUM(propertyvalue - buyingprice - expenses) AS yearly_net_profit " +
                "FROM ownedproperties " +
                "WHERE EXTRACT(YEAR FROM date) = ? " +
                "AND userid = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(year));
            statement.setInt(2, userId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                yearlyNetProfit = resultSet.getDouble("yearly_net_profit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return yearlyNetProfit;
    }

    public static double calculateYearlyTotalProfit(String year, int userId) {
        double yearlyTotalProfit = 0.0;

        String query = "SELECT SUM(propertyvalue - buyingprice) AS yearly_total_profit " +
                "FROM ownedproperties " +
                "WHERE EXTRACT(YEAR FROM date) = ? " +
                "AND userid = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, Integer.parseInt(year));
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                yearlyTotalProfit = resultSet.getDouble("yearly_total_profit");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return yearlyTotalProfit;
    }

    public static List<String> searchProperty(int userId, String propertyType, String country, int yearBuilt, String priceRange, String featureRange) {
        List<String> searchResults = new ArrayList<>();
        String sql = "SELECT * FROM ownedproperties WHERE userid = ?";
        if (!"All".equals(propertyType)) {
            sql += " AND propertytype = ?";
        }
        if (!"All".equals(country)) {
            sql += " AND country = ?";
        }
        if (yearBuilt != 0) {
            sql += " AND yearbuilt = ?";
        }
        if (!"All".equals(priceRange)) {
            if (priceRange.equals("$0 - $499,999")) {
                sql += " AND buyingprice >= 0 AND buyingprice <= 499999";
            } else if (priceRange.equals("$500,000 - $1,000,000")) {
                sql += " AND buyingprice >= 500000 AND buyingprice <= 1000000";
            } else if (priceRange.equals("$1,000,000+")) {
                sql += " AND buyingprice >= 1000000";
            }
        }
        if (!"All".equals(featureRange)) {
            sql += " AND features LIKE ?";
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int parameterIndex = 1;
            pstmt.setInt(parameterIndex++, userId);

            if (!"All".equals(propertyType)) {
                pstmt.setString(parameterIndex++, propertyType);
            }
            if (!"All".equals(country)) {
                pstmt.setString(parameterIndex++, country);
            }
            if (yearBuilt != 0) {
                pstmt.setInt(parameterIndex++, yearBuilt);
            }
            if (!"All".equals(featureRange)) {
                pstmt.setString(parameterIndex++, "%" + featureRange + "%");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String result = rs.getString("propertyname");
                    searchResults.add(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    public static void checkResponseAlert(int loggedInID) {
        java.util.Date currentDate = new java.util.Date();

        String query = "SELECT contactdate FROM interestedproperties WHERE userid = ? AND contactdate < ? AND responsereceived = false";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, loggedInID);
            preparedStatement.setDate(2, new java.sql.Date(currentDate.getTime() - 7 * 24 * 3600 * 1000));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "You have interested properties that are more than 7 days old and no response has been received.", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
