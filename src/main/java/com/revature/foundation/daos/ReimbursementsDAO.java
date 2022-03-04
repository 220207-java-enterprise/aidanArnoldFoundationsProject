package com.revature.foundation.daos;

import com.revature.foundation.models.*;
import com.revature.foundation.util.Bytea;
import com.revature.foundation.util.connectionFactory;
import com.revature.foundation.util.exceptions.DataSourceException;
import com.revature.foundation.util.exceptions.ResourcePersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementsDAO implements CrudDAO<Reimbursements> {

    private final String rootSelect = "SELECT " +
            "er.reimb_id, er.amount, er.submitted, er.resolved, er.description, er.receipt, er.payment_id, er.author_id, er.resolver_id, er.status_id, er.type_id , et.type, ers.status " +
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
        try (Connection conn = connectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt3 = conn.prepareStatement("INSERT INTO ers_reimbursements VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt3.setString(1, newObject.getReimbId());
            pstmt3.setDouble(2, newObject.getAmount());
            pstmt3.setString(3, newObject.getSubmitted());
            pstmt3.setString(4, newObject.getResolved());
            pstmt3.setString(5, newObject.getDescription());
            pstmt3.setBinaryStream(6, newObject.getReceipt().getBinaryStream());
            pstmt3.setString(7, newObject.getPaymentId());
            pstmt3.setString(8, newObject.getAuthorId());
            pstmt3.setString(9, newObject.getResolverId());
            pstmt3.setString(10, newObject.getStatusId().getStatus());
            pstmt3.setString(11, newObject.getTypeId().getType());

            int rowsInserted3 = pstmt3.executeUpdate();
            if (rowsInserted3 != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to persist user to data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public Reimbursements getById(String id) {
        Reimbursements reimbursements = null;

        try (Connection conn = connectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(rootSelect + "WHERE reimb_id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                reimbursements = new Reimbursements();

                reimbursements.setReimbId(rs.getString("reimb_id"));
                reimbursements.setAmount(rs.getInt("amount"));
                reimbursements.setSubmitted(rs.getString("submitted"));
                reimbursements.setResolved(rs.getString("resolved"));
                reimbursements.setDescription(rs.getString("description"));
//                reimbursements.setReceipt(new Bytea(rs.getBytes("receipt")));
                reimbursements.setPaymentId(rs.getString("payment_id"));
                reimbursements.setAuthorId(rs.getString("author_id"));
                reimbursements.setResolverId(rs.getString("resolver_id"));
                reimbursements.setStatusId(new ReimbursementStatuses(rs.getString("status_id"), rs.getString("status")));
                reimbursements.setTypeId(new ReimbursementTypes(rs.getString("type_id"), rs.getString("type")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return reimbursements;

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
                reimbursement.setSubmitted(rs.getString("submitted"));
                reimbursement.setResolved(rs.getString("resolved"));
                reimbursement.setDescription(rs.getString("description"));
//                reimbursement.setReceipt(new Bytea(rs.getBytes("receipt")));
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

        try (Connection conn = connectionFactory.getInstance().getConnection()) {

            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_reimbursements " +
                    "SET amount = ?, " +
                    "resolved = NULL, " +
                    "description = ?, " +
                    "receipt = NULL, " +
                    "payment_id = ?, " +
                    "author_id = ?, " +
                    "resolver_id = ?, " +
                    "status_id = ?, " +
                    "type_id = ? " +
                    "WHERE reimb_id = ?");

            pstmt.setInt(1, updatedObject.getAmount());
//            pstmt.setString(2, updatedObject.getResolved());
            pstmt.setString(2, updatedObject.getDescription());
//            pstmt.setBinaryStream(4, updatedObject.getReceipt().getBinaryStream());
            pstmt.setString(3, updatedObject.getPaymentId());
            pstmt.setString(4,updatedObject.getAuthorId());
            pstmt.setString(5, updatedObject.getResolverId());
            pstmt.setString(6, updatedObject.getStatusId().getStatus());
            pstmt.setString(7, updatedObject.getTypeId().getType());
            pstmt.setString(8, updatedObject.getReimbId());



            // TODO allow role to be updated as well

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                throw new ResourcePersistenceException("Failed to update user data within datasource.");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

    }

    @Override
    public void deleteById(String id) {

    }
}
