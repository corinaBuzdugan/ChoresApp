package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParentAccount {
    private String email;
    private String password;
    private int id; // Unique identifier for the parent account

    public ParentAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    // Method to create a new parent account
    public boolean createAccount() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String checkQuery = "SELECT * FROM parents WHERE email = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, email);
                ResultSet checkResult = checkStatement.executeQuery();

                if (checkResult.next()) {
                    // Email already exists, can't create an account
                    return false;
                } else {
                    String insertQuery = "INSERT INTO parents (email, password) VALUES (?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        insertStatement.setString(1, email);
                        insertStatement.setString(2, password);
                        int affectedRows = insertStatement.executeUpdate();

                        if (affectedRows > 0) {
                            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                id = generatedKeys.getInt(1); // Get the generated parent ID
                            }
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to log in a parent account
    public boolean login() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM parents WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);
                ResultSet result = statement.executeQuery();

                if (result.next()) {
                    id = result.getInt("id");
                    return true; // Parent account found and logged in
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Login failed
    }
}
