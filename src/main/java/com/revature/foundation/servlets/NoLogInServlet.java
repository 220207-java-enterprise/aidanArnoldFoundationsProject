package com.revature.foundation.servlets;


import com.revature.foundation.dtos.requests.LoginRequest;
import com.revature.foundation.dtos.responses.Principal;
import com.revature.foundation.services.TokenService;
import com.revature.foundation.services.UserService;
import com.revature.foundation.util.exceptions.AuthenticationException;
import com.revature.foundation.util.exceptions.InvalidRequestException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class NoLogInServlet extends HttpServlet {

        private final TokenService tokenService;

        public NoLogInServlet(TokenService tokenService) {
            this.tokenService = tokenService;

        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
            System.out.println(req.getServletContext().getInitParameter("programmaticParam"));
        }

        // Login endpoint
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

            PrintWriter writer = resp.getWriter();

            try {


                String payload = "This is who is logged in: " + tokenService.extractRequesterDetails(req.getHeader("Authorization"));
                writer.write(payload);

            } catch (InvalidRequestException e) {
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