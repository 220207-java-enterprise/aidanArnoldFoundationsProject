package com.revature.foundation.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.foundation.daos.UsersDAO;
import com.revature.foundation.dtos.requests.LoginRequest;
import com.revature.foundation.dtos.responses.Principal;
import com.revature.foundation.dtos.responses.UpdatedUserReponse;
import com.revature.foundation.models.Users;
import com.revature.foundation.services.TokenService;
import com.revature.foundation.services.UserService;
import com.revature.foundation.util.exceptions.AuthenticationException;
import com.revature.foundation.util.exceptions.InvalidRequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AdminUserUpdateServlet extends HttpServlet {

        private final TokenService tokenService;
        private final UserService userService;
        private final ObjectMapper mapper;

        public AdminUserUpdateServlet(TokenService tokenService, UserService userService, ObjectMapper mapper) {
            this.tokenService = tokenService;
            this.userService = userService;
            this.mapper = mapper;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println(req.getServletContext().getInitParameter("programmaticParam"));
        }

        // Login endpoint
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            PrintWriter writer = resp.getWriter();

            try {

                LoginRequest loginRequest = mapper.readValue(req.getInputStream(), LoginRequest.class);
                Principal principal = new Principal(userService.login(loginRequest));



                UpdatedUserReponse updatedUserReponse = new UpdatedUserReponse(userService.login(loginRequest));


                String header = String.valueOf(tokenService.extractRequesterDetails(req.getHeader("Authorization")));
                StringBuilder userIdFromHeader = new StringBuilder();
//                int count = 0;
//                for (String s:header.split("\'(,\')*")) {
//                    if(count == 1) {
//                        userIdFromHeader.append(s);
////            System.out.println(s);
//                    }
//                    count++;
//                }
//                UsersDAO daoToPullUserForRole_Id = new UsersDAO();
//                Users theUserFromHeader = daoToPullUserForRole_Id.getById(String.valueOf(userIdFromHeader));
//                String uFHuser = theUserFromHeader.getUsername();
//                String uFHpass = theUserFromHeader.getPassword();
//                String uFHrole = theUserFromHeader.getRole().getRoleName();

//                Principal principal2 = new Principal(userService.login(uFHuser, uFHpass));
//                String payload3 = mapper.writeValueAsString(principal2);

                String payload1 = "This is who is logged in: "+ tokenService.extractRequesterDetails(req.getHeader("Authorization"));
                String payload2 = "This is who we are going to change " + mapper.writeValueAsString(updatedUserReponse);

                String payload = payload1 + payload2 + "what Need";



                String token = tokenService.generateToken(principal);
                resp.setHeader("Authorization", token);
                resp.setContentType("application/json");

                Principal potentiallyAdmin = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
//                if((uFHrole.equals("Admin"))){
//                    throw new InvalidRequestException();
//                }
                writer.write(payload);


            } catch (InvalidRequestException | DatabindException e) {
                e.printStackTrace();
                resp.setStatus(400);
            } catch (AuthenticationException e) {
                resp.setStatus(401); // UNAUTHORIZED (no user found with provided credentials)
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(500);
            }
        }

    }
