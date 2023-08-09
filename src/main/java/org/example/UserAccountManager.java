package org.example;

import java.sql.*;

public class UserAccountManager {
    private static Connection connection;

    public UserAccountManager(String parentEmail, String parentPassword) {
    }

    // Method to establish a connection to the database
    private static void connectToDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/chores_db";
            String username = "postgres";
            String password = "Pr1ngl3s";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean createParentAccount(String email, String password) {
        connectToDatabase();
        try {
            // Check if the parent's email already exists in the database
            String checkQuery = "SELECT * FROM parents WHERE email = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, email);
            ResultSet checkResult = checkStatement.executeQuery();

            if (checkResult.next()) {
                // Email already exists, can't create an account
                return false;
            } else {
                // Create a new parent account
                String insertQuery = "INSERT INTO parents (email, password) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, email);
                insertStatement.setString(2, password);
                insertStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public static boolean createChildAccount(String username, String password, int parentId) {
        connectToDatabase();
        try {
            // Check if the child's username already exists in the database
            String checkQuery = "SELECT * FROM children WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, username);
            ResultSet checkResult = checkStatement.executeQuery();

            if (checkResult.next()) {
                // Username already exists, can't create an account
                return false;
            } else {
                // Create a new child account linked to the parent's ID
                String insertQuery = "INSERT INTO children (username, password, parent_id) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                insertStatement.setInt(3, parentId);
                insertStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public static boolean loginParent(String email, String password) {
        connectToDatabase();
        try {
            String query = "SELECT * FROM parents WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public static boolean loginChild(String username, String password) {
        connectToDatabase();
        try {
            String query = "SELECT * FROM children WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    private static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <ChildAccount> ChildAccount addChildAccount(String childUsername, String childPassword) {
        return null;
    }
}