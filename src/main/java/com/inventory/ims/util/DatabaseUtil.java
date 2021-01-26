package com.inventory.ims.util;


import java.sql.*;
import java.util.concurrent.TimeUnit;


public class DatabaseUtil {

  private static String url = "jdbc:mysql://database-1.c6ltym5semvf.us-east-2.rds.amazonaws.com/ims_SKU";
  private static String user = "admin";
  private static String password = "cs5200proj";

//    private static String url = "jdbc:mysql://localhost:3306/ims_sku?serverTimezone=EST5EDT";
//    private static String user = "weihan";
//    private static String password = "lwh@123456";


    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert one record into the database, return its id, -1 if the insertion failed.
     * @param insertSQL
     * @return
     */
    public static int insertOneRecord(String insertSQL) {
        try (Connection con = DatabaseUtil.createConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);
        return DatabaseUtil.getGeneratedId(stmt);
        } catch (SQLException e) {
        e.printStackTrace();
        return -1;
        }
    }

    public static int getGeneratedId(Statement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        return rs.next() ? rs.getInt(1) : -1;
    }

    public static void timer() {
        System.out.println(TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS));
    }


}




