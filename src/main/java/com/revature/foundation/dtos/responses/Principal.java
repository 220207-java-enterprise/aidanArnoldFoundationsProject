package com.revature.foundation.dtos.responses;

import com.revature.foundation.models.Reimbursements;
import com.revature.foundation.models.Users;

public class Principal {

    private String userId;
    private String username;
    private String roleId;

    public Principal() {
        super();
    }

    public Principal(Users user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.roleId = user.getRole().getRoleName();
    }

    public Principal(Reimbursements reimbursements) {
        this.userId = reimbursements.getAuthorId();
        this.username = reimbursements.getReimbId();
        this.roleId = reimbursements.getTypeId().getType();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
