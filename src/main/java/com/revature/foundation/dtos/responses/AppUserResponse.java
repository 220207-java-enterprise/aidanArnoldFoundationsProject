package com.revature.foundation.dtos.responses;

import com.revature.foundation.models.Users;
public class AppUserResponse {

    private String userId;
    private String givenName;
    private String surname;
    private String username;
    private String roleId;

    public AppUserResponse() {
        super();
    }


    public AppUserResponse(Users user) {
        this.userId = user.getUserId();
        this.givenName = user.getGivenName();
        this.surname = user.getSurname();
        this.username = user.getUsername();
        this.roleId = user.getRole().getRoleName();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
}
