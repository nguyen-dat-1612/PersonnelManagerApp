package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class PayrollResponse {
    @SerializedName("workDays")
    private int workDays;
    @SerializedName("actualWorkDays")
    private double actualWorkDays;
    @SerializedName("unpaidLeaveDays")
    private double unpaidLeaveDays;
    @SerializedName("salary")
    private double salary;
    @SerializedName("seniority")
    private double seniority;
    @SerializedName("userId")
    private long userId;
    @SerializedName("fullName")
    private String fullName;

    public PayrollResponse(double actualWorkDays, double unpaidLeaveDays, int workDays, double salary, double seniority, long userId, String fullName) {
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
}