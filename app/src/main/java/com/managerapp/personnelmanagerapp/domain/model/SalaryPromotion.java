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

    private SalaryPromotion(Builder builder) {
        this.id = builder.id;
        this.date = builder.date;
        this.status = builder.status;
        this.note = builder.note;
        this.reason = builder.reason;
        this.userName = builder.userName;
        this.signerName = builder.signerName;
        this.currentJobGradeName = builder.currentJobGradeName;
        this.requestJobGradeName = builder.requestJobGradeName;
        this.requestJobGradeValue = builder.requestJobGradeValue;
    }

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
        if (o == this) return true;
        if (!(o instanceof SalaryPromotion)) return false;
        SalaryPromotion that = (SalaryPromotion) o;
        return id == that.id &&
                Double.compare(requestJobGradeValue, that.requestJobGradeValue) == 0 &&
                Objects.equals(date, that.date) &&
                Objects.equals(status, that.status) &&
                Objects.equals(note, that.note) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(signerName, that.signerName) &&
                Objects.equals(currentJobGradeName, that.currentJobGradeName) &&
                Objects.equals(requestJobGradeName, that.requestJobGradeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, status, note, reason, userName, signerName, currentJobGradeName, requestJobGradeName, requestJobGradeValue);
    }

    public static class Builder {
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

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder signerName(String signerName) {
            this.signerName = signerName;
            return this;
        }

        public Builder currentJobGradeName(String currentJobGradeName) {
            this.currentJobGradeName = currentJobGradeName;
            return this;
        }

        public Builder requestJobGradeName(String requestJobGradeName) {
            this.requestJobGradeName = requestJobGradeName;
            return this;
        }

        public Builder requestJobGradeValue(double requestJobGradeValue) {
            this.requestJobGradeValue = requestJobGradeValue;
            return this;
        }

        public SalaryPromotion build() {
            return new SalaryPromotion(this);
        }
    }
}
