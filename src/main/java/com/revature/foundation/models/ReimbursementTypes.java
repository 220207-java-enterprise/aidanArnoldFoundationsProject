package com.revature.foundation.models;

public class ReimbursementTypes {
    private String typeId;
    private String type;

    public ReimbursementTypes() {
        super();
    }

    public ReimbursementTypes(String typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReimbursementTypes{" +
                "typeId='" + typeId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
