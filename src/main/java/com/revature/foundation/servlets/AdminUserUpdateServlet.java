package com.revature.foundation.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.foundation.dtos.requests.LoginRequest;
import com.revature.foundation.dtos.responses.Principal;
import com.revature.foundation.dtos.responses.UpdatedUserReponse;
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

                Principal potentiallyAdmin = tokenService.extractRequesterDetails(req.getHeader("Authorization"));
                if(!(potentiallyAdmin.getRoleId().equals("Admin"))){
                    throw new InvalidRequestException();
                }

                UpdatedUserReponse updatedUserReponse = new UpdatedUserReponse(userService.login(loginRequest));

                String payload = mapper.writeValueAsString(updatedUserReponse);

//             Stateful session management
//            HttpSession httpSession = req.getSession();
//            httpSession.setAttribute("authUser", principal);

                String token = tokenService.generateToken(principal);
                resp.setHeader("Authorization", token);
                resp.setContentType("application/json");
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
