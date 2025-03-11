package com.managerapp.personnelmanagerapp.domain.model;

// Class hợp đồng
public class Contract {
    private String id;
    private int employeeId;
    private String contractTypeId;
    private String startDate;
    private String endDate;
    private double salary;
    private String status;

    public Contract(String id, double salary, String status, String endDate, String startDate, String contractTypeId, int employeeId) {
        this.id = id;
        this.salary = salary;
        this.status = status;
        this.endDate = endDate;
        this.startDate = startDate;
        this.contractTypeId = contractTypeId;
        this.employeeId = employeeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
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

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
