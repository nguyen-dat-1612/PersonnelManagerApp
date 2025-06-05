package com.managerapp.personnelmanagerapp.domain.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SalaryPromotion that = (SalaryPromotion) o;
        return id == that.id && Double.compare(requestJobGradeValue, that.requestJobGradeValue) == 0 && Objects.equals(date, that.date) && Objects.equals(status, that.status) && Objects.equals(note, that.note) && Objects.equals(reason, that.reason) && Objects.equals(userName, that.userName) && Objects.equals(signerName, that.signerName) && Objects.equals(currentJobGradeName, that.currentJobGradeName) && Objects.equals(requestJobGradeName, that.requestJobGradeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, status, note, reason, userName, signerName, currentJobGradeName, requestJobGradeName, requestJobGradeValue);
    }
}
