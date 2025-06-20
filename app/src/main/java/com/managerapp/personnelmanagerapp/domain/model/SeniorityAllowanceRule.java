package com.managerapp.personnelmanagerapp.domain.model;

import java.util.Date;
import java.util.Objects;

public class SeniorityAllowanceRule {
    private int id;
    private int minService;
    private double seniorityPercentage;
    private int seniorityLeaveDay;
    private Date effectiveDate;
    private Date expiryDate;
    private String description;
    private UserSummary signer;

    private SeniorityAllowanceRule(Builder builder) {
        this.id = builder.id;
        this.minService = builder.minService;
        this.seniorityPercentage = builder.seniorityPercentage;
        this.seniorityLeaveDay = builder.seniorityLeaveDay;
        this.effectiveDate = builder.effectiveDate;
        this.expiryDate = builder.expiryDate;
        this.description = builder.description;
        this.signer = builder.signer;
    }

    public int getId() {
        return id;
    }

    public int getMinService() {
        return minService;
    }

    public double getSeniorityPercentage() {
        return seniorityPercentage;
    }

    public int getSeniorityLeaveDay() {
        return seniorityLeaveDay;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public String getDescription() {
        return description;
    }

    public UserSummary getSigner() {
        return signer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeniorityAllowanceRule)) return false;
        SeniorityAllowanceRule that = (SeniorityAllowanceRule) o;
        return id == that.id &&
                minService == that.minService &&
                Double.compare(that.seniorityPercentage, seniorityPercentage) == 0 &&
                seniorityLeaveDay == that.seniorityLeaveDay &&
                Objects.equals(effectiveDate, that.effectiveDate) &&
                Objects.equals(expiryDate, that.expiryDate) &&
                Objects.equals(description, that.description) &&
                Objects.equals(signer, that.signer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, minService, seniorityPercentage, seniorityLeaveDay, effectiveDate, expiryDate, description, signer);
    }

    public static class Builder {
        private int id;
        private int minService;
        private double seniorityPercentage;
        private int seniorityLeaveDay;
        private Date effectiveDate;
        private Date expiryDate;
        private String description;
        private UserSummary signer;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder minService(int minService) {
            this.minService = minService;
            return this;
        }

        public Builder seniorityPercentage(double seniorityPercentage) {
            this.seniorityPercentage = seniorityPercentage;
            return this;
        }

        public Builder seniorityLeaveDay(int seniorityLeaveDay) {
            this.seniorityLeaveDay = seniorityLeaveDay;
            return this;
        }

        public Builder effectiveDate(Date effectiveDate) {
            this.effectiveDate = effectiveDate;
            return this;
        }

        public Builder expiryDate(Date expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder signer(UserSummary signer) {
            this.signer = signer;
            return this;
        }

        public SeniorityAllowanceRule build() {
            return new SeniorityAllowanceRule(this);
        }
    }
}
