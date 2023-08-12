package org.example;

import java.sql.*;

public class UserAccountManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/chores_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Pr1ngl3s";

    private String parentEmail;
    private String parentPassword;

    public UserAccountManager(String parentEmail, String parentPassword) {
        this.parentEmail = parentEmail;
        this.parentPassword = parentPassword;
    }

    public boolean createParentAccount(String email, String password) {
        Connection connection = null;
        try {
            connection = getConnection();

            if (emailExists(connection, email)) {
                return false; // Email already exists, can't create an account
            } else {
                insertParentAccount(connection, email, password);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    public int getParentId() {
        Connection connection = null;
        int parentId = -1; // Default value if not found
        try {
            connection = getConnection();
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
        } finally {
            closeConnection(connection);
        }
        return parentId;
    }

    public boolean createChildAccount(String username, String password, int parentId) {
        Connection connection = null;
        try {
            connection = getConnection();

            if (usernameExists(connection, username)) {
                return false; // Username already exists, can't create an account
            } else {
                insertChildAccount(connection, username, password, parentId);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    public boolean loginParent(String email, String password) {
        Connection connection = null;
        try {
            connection = getConnection();
            return parentLoginSuccessful(connection, email, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return false;
    }

    public boolean loginChild(String username, String password) {
        Connection connection = null;
        try {
            connection = getConnection();
            return childLoginSuccessful(connection, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
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

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChildAccount addChildAccount(String childUsername, String childPassword, int parentId) {
        Connection connection = null;
        try {
            connection = getConnection();

            if (usernameExists(connection, childUsername)) {
                return null; // Username already exists, can't create an account
            } else {
                insertChildAccount(connection, childUsername, childPassword, parentId);
                return new ChildAccount(childUsername, childPassword, parentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return null;
    }
}