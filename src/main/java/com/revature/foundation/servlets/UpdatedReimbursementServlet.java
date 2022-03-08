package com.revature.foundation.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.foundation.dtos.requests.UpdatedReimbursementRequest;
import com.revature.foundation.dtos.responses.Principal;
import com.revature.foundation.dtos.responses.UpdatedReimbursementResponse;
import com.revature.foundation.services.ReimbursementService;
import com.revature.foundation.services.TokenService;
import com.revature.foundation.util.exceptions.AuthenticationException;
import com.revature.foundation.util.exceptions.InvalidRequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdatedReimbursementServlet extends HttpServlet {

    private final TokenService tokenService;
    private final ReimbursementService reimbursementService;
    private final ObjectMapper mapper;

    public UpdatedReimbursementServlet(TokenService tokenService, ReimbursementService reimbursementService, ObjectMapper mapper) {
        this.tokenService = tokenService;
        this.reimbursementService = reimbursementService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getServletContext().getInitParameter("programmaticParam"));
    }

    // NewReimbursement endpoint
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        try {

//                LoginRequest loginRequest = mapper.readValue(req.getInputStream(), LoginRequest.class);
//                Principal principal = new Principal(userService.login(loginRequest));

            UpdatedReimbursementRequest updatedReimbursementRequest = mapper.readValue(req.getInputStream(), UpdatedReimbursementRequest.class);
//                System.out.println(updatedUserRequest);
            Principal principal = new Principal(reimbursementService.updatedReimbursement(updatedReimbursementRequest));
//                System.out.println(principal);
            UpdatedReimbursementResponse updatedReimbursementResponse = new UpdatedReimbursementResponse(reimbursementService.updatedReimbursement(updatedReimbursementRequest));


            String header = String.valueOf(tokenService.extractRequesterDetails(req.getHeader("Authorization")));
            StringBuilder userIdFromHeader = new StringBuilder();
            int count = 0;
            for (String s:header.split("\'(,\')*")) {
                if(count == 5) {
                    userIdFromHeader.append(s);
                }
                count++;
            }



            String payload1 = "This is who is logged in: "+ tokenService.extractRequesterDetails(req.getHeader("Authorization"));
            String payload2 = "\n This is who we changed: " + mapper.writeValueAsString(updatedReimbursementResponse);

            String payload = payload1 + payload2;



            String token = tokenService.generateToken(principal);
            resp.setHeader("Authorization", token);
            resp.setContentType("application/json");


            System.out.println(userIdFromHeader);
            if(!(String.valueOf(userIdFromHeader).equals("Finance Manager"))){
                throw new InvalidRequestException();
            }
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
