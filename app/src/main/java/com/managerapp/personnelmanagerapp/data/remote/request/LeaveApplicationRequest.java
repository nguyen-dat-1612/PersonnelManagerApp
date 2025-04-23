package com.managerapp.personnelmanagerapp.data.remote.request;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class LeaveApplicationRequest {
    private final String startDate;
    private final String endDate;
    private final String reason;
    private Long userId;
    private final Integer leaveTypeId;

    public LeaveApplicationRequest(String startDate, String endDate, String reason, Integer leaveTypeId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.leaveTypeId = leaveTypeId;
    }

    public LeaveApplicationRequest(String startDate, String endDate, Long userId, String reason, Integer leaveTypeId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.reason = reason;
        this.leaveTypeId = leaveTypeId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LeaveApplicationRequest{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", reason='" + reason + '\'' +
                ", userId=" + userId +
                ", leaveTypeId=" + leaveTypeId +
                '}';
    }
}
