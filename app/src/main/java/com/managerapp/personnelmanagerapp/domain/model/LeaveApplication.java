package com.managerapp.personnelmanagerapp.domain.model;

public class LeaveApplication {
    private int leaveApplicationId;
    private String leaveType;
    private int employeeId;
    private String startDate;
    private String endDate;
    private String reason;
    private String status;
    private boolean isPaid;

    public LeaveApplication(int leaveApplicationId, String leaveType, int employeeId,
                            String startDate, String endDate, String reason,
                            String status, boolean isPaid) {
        this.leaveApplicationId = leaveApplicationId;
        this.leaveType = leaveType;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
        this.isPaid = isPaid;
    }

    public int getLeaveApplicationId() {
        return leaveApplicationId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public boolean isPaid() {
        return isPaid;
    }
}
