package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserAccountManager {
    private String parentEmail;
    private String parentPassword;
    private int parentId;
    private HashMap<String, ChildAccount> childAccounts;

    public UserAccountManager(String parentEmail, String parentPassword) {
        this.parentEmail = parentEmail;
        this.parentPassword = parentPassword;
        this.parentId = -1;
        this.childAccounts = new HashMap<>();
    }

    public boolean createParentAccount(String email, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (emailExists(connection, email)) {
                return false; // Email already exists, can't create an account
            } else {
                insertParentAccount(connection, email, password);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getParentId() {
        int parentId = -1; // Default value if not found
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT id FROM parents WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, parentEmail);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    parentId = result.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parentId;
    }

    public ChildAccount addChildAccount(String username, String password, int parentId) {
        // Create and add a child account to the childAccounts map
        ChildAccount childAccount = new ChildAccount(username, password, parentId);
        childAccounts.put(username, childAccount);
        return childAccount;
    }

    public boolean loginParent(String email, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            return parentLoginSuccessful(connection, email, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loginChild(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            return childLoginSuccessful(connection, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean emailExists(Connection connection, String email) throws SQLException {
        String query = "SELECT * FROM parents WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            return result.next();
        }
    }

    private boolean usernameExists(Connection connection, String username) throws SQLException {
        String query = "SELECT * FROM children WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            return result.next();
        }
    }

    private void insertParentAccount(Connection connection, String email, String password) throws SQLException {
        String query = "INSERT INTO parents (email, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.executeUpdate();
        }
    }

    private void insertChildAccount(Connection connection, String username, String password, int parentId) throws SQLException {
        String query = "INSERT INTO children (username, password, parent_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, parentId);
            statement.executeUpdate();
        }
    }

    private boolean parentLoginSuccessful(Connection connection, String email, String password) throws SQLException {
        String query = "SELECT * FROM parents WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            return result.next();
        }
    }

    private boolean childLoginSuccessful(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT * FROM children WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            return result.next();
        }
    }
}