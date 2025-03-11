package com.managerapp.personnelmanagerapp.domain.model;

// Class tài khoản
public class Account {
    private int id;
    private String username;
    private String password;
    private boolean status;
    private String roleId;

    public Account(int id, String username, boolean status, String password, String roleId) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.password = password;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
