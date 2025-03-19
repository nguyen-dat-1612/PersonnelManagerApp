package com.managerapp.personnelmanagerapp.domain.model;

public class Contract {
    private String contractId;
    private String employeeId;
    private String contractTypeId;
    private String startDate;
    private String endDate;
    private double baseSalary;
    private double positionAllowance;
    private double seniorityAllowance;
    private int currentJobGrade;
    private boolean isActive;


    public Contract(String contractId, String employeeId, String contractTypeId,
                    String startDate, String endDate, double baseSalary,
                    double positionAllowance, double seniorityAllowance,
                    int currentJobGrade, boolean isActive) {
        this.contractId = contractId;
        this.employeeId = employeeId;
        this.contractTypeId = contractTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.baseSalary = baseSalary;
        this.positionAllowance = positionAllowance;
        this.seniorityAllowance = seniorityAllowance;
        this.currentJobGrade = currentJobGrade;
        this.isActive = isActive;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCurrentJobGrade() {
        return currentJobGrade;
    }

    public void setCurrentJobGrade(int currentJobGrade) {
        this.currentJobGrade = currentJobGrade;
    }

    public double getSeniorityAllowance() {
        return seniorityAllowance;
    }

    public void setSeniorityAllowance(double seniorityAllowance) {
        this.seniorityAllowance = seniorityAllowance;
    }

    public double getPositionAllowance() {
        return positionAllowance;
    }

    public void setPositionAllowance(double positionAllowance) {
        this.positionAllowance = positionAllowance;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getContractTypeId() {
        return contractTypeId;
    }

    public void setContractTypeId(String contractTypeId) {
        this.contractTypeId = contractTypeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
