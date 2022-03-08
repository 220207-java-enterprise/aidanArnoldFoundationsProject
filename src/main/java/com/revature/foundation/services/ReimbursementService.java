package com.revature.foundation.services;

import com.revature.foundation.daos.ReimbursementsDAO;
import com.revature.foundation.daos.UsersDAO;
import com.revature.foundation.dtos.requests.AllReimbursementsByIdRequest;
import com.revature.foundation.dtos.requests.NewReimbursementRequest;
import com.revature.foundation.dtos.requests.UpdatedReimbursementRequest;
import com.revature.foundation.dtos.responses.AppReimbursementResponse;
import com.revature.foundation.models.ReimbursementStatuses;
import com.revature.foundation.models.Reimbursements;
import com.revature.foundation.util.exceptions.AuthenticationException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReimbursementService {
    private ReimbursementsDAO reimbursementsDAO; // a dependency of UserService

    // Constructor injection

    public ReimbursementService(ReimbursementsDAO reimbursementsDAO) {
        this.reimbursementsDAO = reimbursementsDAO;
    }
    private UsersDAO userDAO;
    public List<Reimbursements> getAll() {
        List<Reimbursements> reimbursements = reimbursementsDAO.getAll();
        List<AppReimbursementResponse> reimbursementResponses = new ArrayList<>();
        for (Reimbursements reimbursement : reimbursements) {
            reimbursementResponses.add(new AppReimbursementResponse(reimbursement));
        }

        return reimbursements;
    }

    public Reimbursements create(NewReimbursementRequest newReimbursementRequest) {
        Reimbursements newReimbursement = newReimbursementRequest.extractReimbursement();
        System.out.println("pointer" + newReimbursement.toString());

        //TODO add if reimbursementIsValid logic
        ReimbursementStatuses sdf = new ReimbursementStatuses("0", "PENDING");
        newReimbursement.setReimbId(UUID.randomUUID().toString());
        newReimbursement.setStatusId(sdf);
        reimbursementsDAO.save(newReimbursement);

        return newReimbursement;

    }

    //redundant?
    public Reimbursements updatedReimbursement(UpdatedReimbursementRequest updateRequest) {
        Reimbursements updatedReimbursement = updateRequest.extractReimbursement();

        reimbursementsDAO.update(updatedReimbursement);
        return updatedReimbursement;
    }

    public String AllReimbursementsById(AllReimbursementsByIdRequest allReimbursementsByIdRequest) throws SQLException {
        String allReimbursements = allReimbursementsByIdRequest.extractAuthorid();

        reimbursementsDAO.getAllById(allReimbursements);

        return allReimbursements;
    }

    public List<Reimbursements> getAllReimbursementsById(AllReimbursementsByIdRequest allReimbursementsByIdRequest) throws SQLException {
//        System.out.println("somethings");

        String authorId = allReimbursementsByIdRequest.getAuthorId();
//        System.out.println(authorId);

        List<Reimbursements>  reimbursements = ReimbursementsDAO.getAllById(authorId);
        System.out.println(reimbursements + "asodf");
        if (reimbursements == null) {
            throw new AuthenticationException();
        }

        return reimbursements;

    }

}
