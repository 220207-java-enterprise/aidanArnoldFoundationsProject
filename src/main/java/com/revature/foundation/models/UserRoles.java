package com.revature.foundation.models;

public class UserRoles {
    private String roleId;
    private String role;

    public UserRoles() {
        super();
    }

    public UserRoles(String roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRoles{" +
                "roleId='" + roleId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
