package org.example;
import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/chores_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Pr1ngl3s";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
