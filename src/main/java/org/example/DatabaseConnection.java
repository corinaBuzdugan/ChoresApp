package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/chores_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Pr1ngl3s";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = null; // Ensure the connection reference is cleared
        }
    }
}