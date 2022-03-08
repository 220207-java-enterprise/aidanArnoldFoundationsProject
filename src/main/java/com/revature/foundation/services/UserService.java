package com.revature.foundation.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import com.revature.foundation.dtos.requests.LoginRequest;
import com.revature.foundation.dtos.requests.NewUserRequest;
import com.revature.foundation.dtos.requests.UpdatedUserRequest;
import com.revature.foundation.dtos.responses.AppUserResponse;
import com.revature.foundation.models.UserRole;
import com.revature.foundation.models.Users;
import com.revature.foundation.daos.UsersDAO;
import com.revature.foundation.util.exceptions.AuthenticationException;
import com.revature.foundation.util.exceptions.InvalidRequestException;
import com.revature.foundation.util.exceptions.ResourceConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UsersDAO userDAO; // a dependency of UserService

    // Constructor injection
    //If you only have one constructor then you dont really need this autowired tag ebcause its implied
    @Autowired
    public UserService(UsersDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<Users> getAll() {
        List<Users> users = userDAO.getAll();
        List<AppUserResponse> userResponses = new ArrayList<>();
        for (Users user : users) {
            userResponses.add(new AppUserResponse(user));
        }

        return users;
    }

    //redundant?
    public Users updatedUser(UpdatedUserRequest updateRequest) {
        Users updatedUser = updateRequest.extractUser();

        userDAO.update(updatedUser);
        return updatedUser;
    }

    public Users register(NewUserRequest newUserRequest) {

        Users newUser = newUserRequest.extractUser();

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Bad registration details given.");
        }

        boolean usernameAvailable = isUsernameAvailable(newUser.getUsername());
        boolean emailAvailable = isEmailAvailable(newUser.getEmail());

        if (!usernameAvailable || !emailAvailable) {
            String msg = "The values provided for the following fields are already taken by other users: ";
            if (!usernameAvailable) msg += "username ";
            if (!emailAvailable) msg += "email";
            throw new ResourceConflictException(msg);
        }

        // TODO encrypt provided password before storing in the database

        newUser.setUserId(UUID.randomUUID().toString());
        newUser.setRole(new UserRole("3", "Employee")); // All newly registered users start as BASIC_USER
        newUser.setIsActive(false);
        userDAO.save(newUser);

        return newUser;
    }

    public Users login(String username, String password) {

        if (!isUsernameValid(username) || !isPasswordValid(password)) {
            throw new InvalidRequestException("Invalid credentials provided!");
        }

        // TODO encrypt provided password (assumes password encryption is in place) to see if it matches what is in the DB

        Users authUser = userDAO.findUserByUsernameAndPassword(username, password);

        if (authUser == null) {
            throw new AuthenticationException();
        }

        return authUser;

    }



    private boolean isUserValid(Users appUser) {

        // First name and last name are not just empty strings or filled with whitespace
        if (appUser.getGivenName().trim().equals("") || appUser.getSurname().trim().equals("")) {
            return false;
        }

        // Usernames must be a minimum of 8 and a max of 25 characters in length, and only contain alphanumeric characters.
        if (!isUsernameValid(appUser.getUsername())) {
            return false;
        }

        // Passwords require a minimum eight characters, at least one uppercase letter, one lowercase
        // letter, one number and one special character
        if (!isPasswordValid(appUser.getPassword())) {
            return false;
        }

        // Basic email validation
        return isEmailValid(appUser.getEmail());

    }

    public boolean isEmailValid(String email) {
        if (email == null) return false;
        return email.matches("^[^@\\s]+@[^@\\s.]+\\.[^@.\\s]+$");
    }

    public boolean isUsernameValid(String username) {
        if (username == null) return false;
        return username.matches("^[a-zA-Z0-9]{8,25}");
//        return false;
    }

    public boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
//        return false;
    }

    public boolean isUsernameAvailable(String username) {
        if (username == null || !isUsernameValid(username)) return false;
        return userDAO.findUserByUsername(username) == null;
    }

    public boolean isEmailAvailable(String email) {
        if (email == null || !isEmailValid(email)) return false;
        return userDAO.findUserByEmail(email) == null;
    }

    public Users login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (!isUsernameValid(username) || !isPasswordValid(password)) {
            throw new InvalidRequestException("Invalid credentials provided!");
        }

        // TODO encrypt provided password (assumes password encryption is in place) to see if it matches what is in the DB

        Users authUsers = userDAO.findUserByUsernameAndPassword(username, password);

        if (authUsers == null) {
            throw new AuthenticationException();
        }

        return authUsers;

    }
}
