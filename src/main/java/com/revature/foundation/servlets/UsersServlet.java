package com.revature.foundation.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.foundation.dtos.requests.NewUserRequest;
import com.revature.foundation.dtos.requests.UpdatedUserRequest;
import com.revature.foundation.dtos.responses.AppUserResponse;
import com.revature.foundation.dtos.responses.Principal;
import com.revature.foundation.dtos.responses.ResourceCreationResponse;
import com.revature.foundation.models.Users;
import com.revature.foundation.services.TokenService;
import com.revature.foundation.services.UserService;
import com.revature.foundation.util.exceptions.InvalidRequestException;
import com.revature.foundation.util.exceptions.ResourceConflictException;

import com.revature.foundation.util.exceptions.ResourceConflictException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class UsersServlet extends HttpServlet{

    private static Logger logger = LogManager.getLogger(UsersServlet.class);

    private final TokenService tokenService;
    private final UserService userService;
    private final ObjectMapper mapper;

    public UsersServlet(TokenService tokenService, UserService userService, ObjectMapper mapper) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.debug("UserServlet#doGet invoked with args: " + Arrays.asList(req, resp));

        String[] reqFrags = req.getRequestURI().split("/");
        if (reqFrags.length == 4 && reqFrags[3].equals("availability")) {
            checkAvailability(req, resp);
            logger.debug("UserServlet#doGet returned successfully");
            return; // necessary, otherwise we end up doing more work than was requested
        }

        // TODO implement some security logic here to protect sensitive operations

        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));

        if (requester == null) {
            logger.warn("Unauthenticated request made to UserServlet#doGet");
            resp.setStatus(401);
            return;
        }
        if (!requester.getRoleId().equals("ADMIN")) {
            logger.warn("Unauthorized request made by user: " + requester.getUsername());
            resp.setStatus(403); // FORBIDDEN
            return;
        }

        List<Users> users = userService.getAll();
        String payload = mapper.writeValueAsString(users);
        resp.setContentType("application/json");
        resp.getWriter().write(payload);

        logger.debug("UserServlet#doGet returned successfully");

    }

    // registration endpoint
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter respWriter = resp.getWriter();

        try {

            NewUserRequest newUserRequest = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            Users newUser = userService.register(newUserRequest);
            resp.setStatus(201); // CREATED
            resp.setContentType("application/json");
            String payload = mapper.writeValueAsString(new ResourceCreationResponse(newUser.getUserId()));
            respWriter.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            e.printStackTrace();
            resp.setStatus(400); // BAD REQUEST
        } catch (ResourceConflictException e) {
            resp.setStatus(409); // CONFLICT
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resp.setStatus(500);
        }

    }

    //update user endpoint. Something only an admin can do.
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter respWriter = resp.getWriter();

        try {

            Principal potentiallyAdmin = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
            if(!(potentiallyAdmin.getRoleId().equals("Admin"))){
                throw new InvalidRequestException();
            }

            UpdatedUserRequest anUpdatedUserRequest = mapper.readValue(req.getInputStream(), UpdatedUserRequest.class);
            Users updatedUser = userService.updatedUser(anUpdatedUserRequest);

            resp.setStatus(201); // Succesful
            resp.setContentType("application/json");
            String payload = mapper.writeValueAsString(new ResourceCreationResponse(updatedUser.getUserId()));
            respWriter.write(payload);

        } catch (InvalidRequestException | DatabindException e) {
            e.printStackTrace();
            resp.setStatus(400); // BAD REQUEST
        } catch (ResourceConflictException e) {
            e.printStackTrace();
            resp.setStatus(409); // CONFLICT
        } catch (Exception e) {
            e.printStackTrace(); // include for debugging purposes; ideally log it to a file
            resp.setStatus(500);
        }

    }

    protected void checkAvailability(HttpServletRequest req, HttpServletResponse resp) {
        String usernameValue = req.getParameter("username");
        String emailValue = req.getParameter("email");
        if (usernameValue != null) {
            if (userService.isUsernameAvailable(usernameValue)) {
                resp.setStatus(204); // NO CONTENT
            } else {
                resp.setStatus(409);
            }
        } else if (emailValue != null) {
            if (userService.isEmailAvailable(emailValue)) {
                resp.setStatus(204);
            } else {
                resp.setStatus(409);
            }
        }
    }
}
