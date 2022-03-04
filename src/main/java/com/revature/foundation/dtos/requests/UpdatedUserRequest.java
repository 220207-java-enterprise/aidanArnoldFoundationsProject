package com.revature.foundation.dtos.requests;

import com.revature.foundation.daos.UsersDAO;
import com.revature.foundation.models.UserRole;
import com.revature.foundation.models.Users;

public class UpdatedUserRequest {
    private String userId;
    private String username;
    private String email;
    private String password;
    private String givenName;
    private String surname;
    private Boolean isActive;
    private String role;

    public UpdatedUserRequest() {
        super();
    }

    public UpdatedUserRequest(String userId, String username, String email, String password, String givenName, String surname, Boolean isActive, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.givenName = givenName;
        this.surname = surname;
        this.isActive = isActive;
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Users extractUser() {
//        UsersDAO daoToPullUserForRole_Id = new UsersDAO();
//        Users pulledUser = daoToPullUserForRole_Id.getById(this.userId);
////        Users pulledUser = otherVar.getById(this.role);
//        UserRole aRole = new UserRole(pulledUser.getRole().getId(), role);
//        System.out.println("tsate" + pulledUser);
//        return pulledUser;

        UsersDAO daoToPullUserForRole_Id = new UsersDAO();
        Users pulledUser = daoToPullUserForRole_Id.getById(this.userId);
        UserRole aRole = new UserRole(pulledUser.getRole().getId(), role);
        return new Users(this.userId, this.username, this.email, this.password, this.givenName, this.surname, this.isActive, aRole);
    }

    @Override
    public String toString() {
        return "UpdatedUserRequest{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", isActive='" + isActive + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
