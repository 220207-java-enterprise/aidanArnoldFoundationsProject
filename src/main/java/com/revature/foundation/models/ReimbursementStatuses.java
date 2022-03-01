package com.revature.foundation.models;

public class ReimbursementStatuses {
    private String statusId;
    private String status;

    public ReimbursementStatuses() {
        super();
    }

    public ReimbursementStatuses(String statusId, String status) {
        this.statusId = statusId;
        this.status = status;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReimbursementStatuses{" +
                "statusId='" + statusId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
