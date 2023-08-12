package org.example;

public class ChildAccount {
    private String username;
    private String password;
    private int parentId;

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
