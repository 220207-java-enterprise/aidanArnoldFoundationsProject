package com.revature.foundation.daos;

import com.revature.foundation.models.*;
import com.revature.foundation.util.connectionFactory;
import com.revature.foundation.util.exceptions.DataSourceException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementsDAO implements CrudDAO<Reimbursements> {

    private final String rootSelect = "SELECT " +
            "er.reimb_id, er.amount, er.submitted, er.resolved, er.description, er.receipt, er.payment_id, er.author_id, ur.resolver_id, ur.status_id, ur.type_id " +
            "FROM ers_reimbursements er " +
            "JOIN ers_reimbursement_types et " +
            "ON er.type_id = et.type_id " +
            "JOIN ers_reimbursement_statuses ers " +
            "ON er.status_id = ers.status_id ";



    public Reimbursements filterReimbursementByType(String username, String password) {


        return null;

    }

    public Reimbursements filterReimbursementByStatus(String username, String password) {


        return null;

    }

    @Override
    public void save(Reimbursements newObject) {

    }

    @Override
    public Reimbursements getById(String id) {
        return null;
    }

    @Override
    public List<Reimbursements> getAll() {
        List<Reimbursements> reimbursements = new ArrayList<>();

        try (Connection conn = connectionFactory.getInstance().getConnection()) {

            ResultSet rs = conn.createStatement().executeQuery(rootSelect);
            while (rs.next()) {
                Reimbursements reimbursement = new Reimbursements();
                reimbursement.setReimbId(rs.getString("reimb_id"));
                reimbursement.setAmount(rs.getInt("amount"));
                reimbursement.setSubmitted(rs.getInt("submitted"));
                reimbursement.setResolved(rs.getInt("resolved"));
                reimbursement.setDescription(rs.getString("description"));
                reimbursement.setReceipt(rs.getByte("receipt"));
                reimbursement.setPaymentId(rs.getString("payment_id"));
                reimbursement.setAuthorId(rs.getString("author_id"));
                reimbursement.setResolverId(rs.getString("resolver_id"));
                reimbursement.setStatusId(new ReimbursementStatuses(rs.getString("status_id"), rs.getString("status")));
                reimbursement.setTypeId(new ReimbursementTypes(rs.getString("type_id"), rs.getString("type")));
                reimbursements.add(reimbursement);
            }
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return reimbursements;
    }

    @Override
    public void update(Reimbursements updatedObject) {

    }

    @Override
    public void deleteById(String id) {

    }
}
