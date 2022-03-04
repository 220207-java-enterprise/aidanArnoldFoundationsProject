package com.revature.foundation.dtos.requests;

import com.revature.foundation.daos.ReimbursementsDAO;
import com.revature.foundation.models.*;
import com.revature.foundation.util.Bytea;

public class UpdatedReimbursementRequest {

        private String reimbId;
        private int amount;
        private String submitted;
        private String resolved;
        private String description;
        private Bytea receipt;
        private String paymentId;
        private String authorId;
        private String resolverId;
        private String statusId;
        private String typeId;

        public UpdatedReimbursementRequest() {
            super();
        }

        public UpdatedReimbursementRequest(String reimbId, int amount, String submitted, String resolved, String description, Bytea receipt, String paymentId, String authorId, String resolverId, String statusId, String typeId) {
            this.reimbId = reimbId;
            this.amount = amount;
            this.submitted = submitted;
            this.resolved = resolved;
            this.description = description;
            this.receipt = receipt;
            this.paymentId = paymentId;
            this.authorId = authorId;
            this.resolverId = resolverId;
            this.statusId = statusId;
            this.typeId = typeId;
        }

        public String getReimbId() {
            return reimbId;
        }

        public void setReimbId(String reimbId) {
            this.reimbId = reimbId;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getSubmitted() {
            return submitted;
        }

        public void setSubmitted(String submitted) {
            this.submitted = submitted;
        }

        public String getResolved() {
            return resolved;
        }

        public void setResolved(String resolved) {
            this.resolved = resolved;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Bytea getReceipt() {
            return receipt;
        }

        public void setReceipt(Bytea receipt) {
            this.receipt = receipt;
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getAuthorId() {
            return authorId;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public String getResolverId() {
            return resolverId;
        }

        public void setResolverId(String resolverId) {
            this.resolverId = resolverId;
        }

        public String getStatusId() {
            return statusId;
        }

        public void setStatusId(String statusId) {
            this.statusId = statusId;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

    public Reimbursements extractReimbursement() {

        ReimbursementsDAO daoToPullUserForRole_Id = new ReimbursementsDAO();
        Reimbursements pulledReimbursement = daoToPullUserForRole_Id.getById(this.reimbId);

        ReimbursementStatuses aStatus = new ReimbursementStatuses(pulledReimbursement.getStatusId().getStatus(), statusId);
        ReimbursementTypes aType = new ReimbursementTypes(pulledReimbursement.getTypeId().getType(), statusId);
        return new Reimbursements(this.reimbId, this.amount, this.submitted, this.resolved, this.description, this.receipt, this.paymentId, this.authorId, this.resolverId, aStatus, aType);
    }

    @Override
    public String toString() {
        return "NewReimbursementRequest{" +
                "reimbId='" + reimbId + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", paymentId='" + paymentId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", resolverId='" + resolverId + '\'' +
                ", statusId='" + statusId + '\'' +
                ", typeId='" + typeId + '\'' +
                '}';
    }
}
