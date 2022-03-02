package com.revature.foundation.models;

import java.util.Objects;

// POJO = Plain Ol' Java Object
// Contains NO BUSINESS LOGIC
// Simple encapsulation of some domain object's states
public class Users {

    private String userId;
    private String username;
    private String email;
    private String password;
    private String givenName;
    private String surname;
    private String isActive;
    private UserRole role;

    // TODO create a Role enum

    public Users() {
        super(); // not required, but it bugs me personally not to have it
    }

    public Users(String givenName, String surname, String email, String username, String password) {
        this.givenName = givenName;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }



    public String toFileString() {
        return new StringBuilder(userId).append(":")
                .append(username).append(":")
                .append(email).append(":")
                .append(password).append(":")
                .append(givenName).append(":")
                .append(surname).append(":")
                .append(isActive).append(":")
                .append(role).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users appUser = (Users) o;
        return Objects.equals(userId, appUser.userId) && Objects.equals(username, appUser.username) && Objects.equals(email, appUser.email) && Objects.equals(password, appUser.password) && Objects.equals(givenName, appUser.givenName) && Objects.equals(surname, appUser.surname) && Objects.equals(isActive, appUser.isActive) && Objects.equals(role, appUser.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, password, givenName, surname, isActive, role);
    }

    @Override
    public String toString() {
        return "User{" +
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
