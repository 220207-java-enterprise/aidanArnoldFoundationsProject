package com.revature.foundation.servlets;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.foundation.dtos.requests.AllReimbursementsByIdRequest;
import com.revature.foundation.dtos.requests.UpdatedReimbursementRequest;
import com.revature.foundation.dtos.responses.AllReimbursementsByIdResponse;
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

public class AllReimbursementsByIdServlet extends HttpServlet {

    private final TokenService tokenService;
    private final ReimbursementService reimbursementService;
    private final ObjectMapper mapper;

    public AllReimbursementsByIdServlet(TokenService tokenService, ReimbursementService reimbursementService, ObjectMapper mapper) {
        this.tokenService = tokenService;
        this.reimbursementService = reimbursementService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        try {

//            System.out.println(req.getInputStream());
            AllReimbursementsByIdRequest allReimbursementsByIdRequest = mapper.readValue(req.getInputStream(), AllReimbursementsByIdRequest.class);
            System.out.println(allReimbursementsByIdRequest);
//                System.out.println(updatedUserRequest);
            System.out.println("got here2");

            AllReimbursementsByIdResponse allReimbursementsByIdResponse = new AllReimbursementsByIdResponse(reimbursementService.getAllReimbursementsById(allReimbursementsByIdRequest));
            System.out.println("this" + allReimbursementsByIdResponse + "asdfasdfafdasdf");


            String header = String.valueOf(tokenService.extractRequesterDetails(req.getHeader("Authorization")));
            StringBuilder userIdFromHeader = new StringBuilder();
            int count = 0;
            for (String s:header.split("\'(,\')*")) {
                if(count == 5) {
                    userIdFromHeader.append(s);
                }
                count++;
            }

            System.out.println("got here3");

            String payload1 = "This is who is logged in: "+ tokenService.extractRequesterDetails(req.getHeader("Authorization"));
            String payload2 = "\n This is all of this user's reimbursements: " + mapper.writeValueAsString(allReimbursementsByIdResponse);

            String payload = payload1 + payload2;




//            System.out.println(userIdFromHeader);
//            if(!(String.valueOf(userIdFromHeader).equals("Finance Manager"))){
//                throw new InvalidRequestException();
//            }
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

    // NewReimbursement endpoint
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("got here");
        PrintWriter writer = resp.getWriter();

        try {
            System.out.println("got here1");
            UpdatedReimbursementRequest updatedReimbursementRequest = mapper.readValue(req.getInputStream(), UpdatedReimbursementRequest.class);
            AllReimbursementsByIdRequest allReimbursementsByIdRequest = mapper.readValue(req.getInputStream(), AllReimbursementsByIdRequest.class);
            System.out.println(allReimbursementsByIdRequest);
//                System.out.println(updatedUserRequest);
            System.out.println("got here2");
            UpdatedReimbursementResponse updatedReimbursementResponse = new UpdatedReimbursementResponse(reimbursementService.updatedReimbursement(updatedReimbursementRequest));
            AllReimbursementsByIdResponse allReimbursementsByIdResponse = new AllReimbursementsByIdResponse(reimbursementService.getAllReimbursementsById(allReimbursementsByIdRequest));

            String header = String.valueOf(tokenService.extractRequesterDetails(req.getHeader("Authorization")));
            StringBuilder userIdFromHeader = new StringBuilder();
            int count = 0;
            for (String s:header.split("\'(,\')*")) {
                if(count == 5) {
                    userIdFromHeader.append(s);
                }
                count++;
            }

            System.out.println("got here3");

            String payload1 = "This is who is logged in: "+ tokenService.extractRequesterDetails(req.getHeader("Authorization"));
            String payload2 = "\n This is all of this user's reimbursements: " + mapper.writeValueAsString(allReimbursementsByIdResponse);

            String payload = payload1 + payload2;




//            System.out.println(userIdFromHeader);
//            if(!(String.valueOf(userIdFromHeader).equals("Finance Manager"))){
//                throw new InvalidRequestException();
//            }
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