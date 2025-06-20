package com.managerapp.personnelmanagerapp.domain.model;

public class Payroll {
    private int workDays;
    private double actualWorkDays;
    private double unpaidLeaveDays;
    private double salary;
    private double seniority;
    private long userId;
    private String fullName;

    public Payroll(double actualWorkDays, double unpaidLeaveDays, int workDays, double salary, double seniority, long userId, String fullName) {
        this.actualWorkDays = actualWorkDays;
        this.unpaidLeaveDays = unpaidLeaveDays;
        this.workDays = workDays;
        this.salary = salary;
        this.seniority = seniority;
        this.userId = userId;
        this.fullName = fullName;
    }

    public int getWorkDays() {
        return workDays;
    }

    public double getActualWorkDays() {
        return actualWorkDays;
    }

    public double getUnpaidLeaveDays() {
        return unpaidLeaveDays;
    }

    public double getSalary() {
        return salary;
    }

    public double getSeniority() {
        return seniority;
    }

    public long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int workDays;
        private double actualWorkDays;
        private double unpaidLeaveDays;
        private double salary;
        private double seniority;
        private long userId;
        private String fullName;

        public Builder workDays(int workDays) {
            this.workDays = workDays;
            return this;
        }

        public Builder actualWorkDays(double actualWorkDays) {
            this.actualWorkDays = actualWorkDays;
            return this;
        }

        public Builder unpaidLeaveDays(double unpaidLeaveDays) {
            this.unpaidLeaveDays = unpaidLeaveDays;
            return this;
        }

        public Builder salary(double salary) {
            this.salary = salary;
            return this;
        }

        public Builder seniority(double seniority) {
            this.seniority = seniority;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Payroll build() {
            return new Payroll(actualWorkDays, unpaidLeaveDays, workDays, salary, seniority, userId, fullName);
        }
    }
}
