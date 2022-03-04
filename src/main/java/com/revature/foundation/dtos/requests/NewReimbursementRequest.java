package com.revature.foundation.dtos.requests;

import com.revature.foundation.models.ReimbursementStatuses;
import com.revature.foundation.models.ReimbursementTypes;
import com.revature.foundation.models.Reimbursements;
import com.revature.foundation.util.Bytea;

public class NewReimbursementRequest {
    private int amount;
    private String submitted;
    private String resolved;
    private String description;
    private Bytea receipt;
    private String paymentId;
    private String authorId;
    private String resolverId;
    private ReimbursementStatuses statusId;
    private ReimbursementTypes typeId;

    public NewReimbursementRequest() {
        super();
    }

    public NewReimbursementRequest(int amount, String submitted, String resolved, String description, Bytea receipt, String paymentId, String authorId, String resolverId, ReimbursementStatuses statusId, ReimbursementTypes typeId) {
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

    public ReimbursementStatuses getStatusId() {
        return statusId;
    }

    public void setStatusId(ReimbursementStatuses statusId) {
        this.statusId = statusId;
    }

    public ReimbursementTypes getTypeId() {
        return typeId;
    }

    public void setTypeId(ReimbursementTypes typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "NewReimbursementRequest{" +
                "amount=" + amount +
                ", submitted='" + submitted + '\'' +
                ", resolved='" + resolved + '\'' +
                ", description='" + description + '\'' +
                ", receipt=" + receipt +
                ", paymentId='" + paymentId + '\'' +
                ", authorId='" + authorId + '\'' +
                ", resolverId='" + resolverId + '\'' +
                ", statusId=" + statusId +
                ", typeId=" + typeId +
                '}';
    }

    public Reimbursements extractReimbursement() {
        return new Reimbursements(amount, submitted, resolved, description, receipt, paymentId, authorId, resolverId, statusId, typeId);
    }
}
