package com.managerapp.personnelmanagerapp.domain.model;

public class SalaryPromotion {
    private long id;
    private String date;
    private String status;
    private String note;
    private String reason;
    private String userName;
    private String signerName;
    private String currentJobGradeName;
    private String requestJobGradeName;
    private double requestJobGradeValue;

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public String getReason() {
        return reason;
    }

    public String getUserName() {
        return userName;
    }

    public String getSignerName() {
        return signerName;
    }

    public String getCurrentJobGradeName() {
        return currentJobGradeName;
    }

    public String getRequestJobGradeName() {
        return requestJobGradeName;
    }

    public double getRequestJobGradeValue() {
        return requestJobGradeValue;
    }
}
