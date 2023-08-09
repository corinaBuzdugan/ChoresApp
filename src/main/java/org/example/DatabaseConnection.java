package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            Class.forName("org.postgresql.Driver");
            String host = "localhost";
            String port = "5432";
            String dbName = "chores_db";
            String username = "postgres";
            String password = "Pr1ngl3s";

            String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}