package org.example;

public class ChildAccount {
    private final String username;
    private final String password;
    private final int parentId;

    public ChildAccount(String username, String password, int parentId) {
        this.username = username;
        this.password = password;
        this.parentId = parentId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getParentId() {
        return parentId;
    }
}