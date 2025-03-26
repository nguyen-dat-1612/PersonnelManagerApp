package com.managerapp.personnelmanagerapp.domain.model;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LeaveApplication {
    private int leaveApplicationId;  // Mã đơn xin nghỉ phép
    private String leaveType;        // Loại nghỉ phép
    private int userId;              // Mã người dùng
    private Date startDate;          // Ngày bắt đầu nghỉ
    private Date endDate;            // Ngày kết thúc nghỉ
    private String reason;           // Lý do nghỉ
    private LeaveStatus status;      // Trạng thái đơn
    private boolean isPaid;         // Có tính lương hay không

    // Enum for leave status
    public enum LeaveStatus {
        PENDING("Đang chờ"),
        APPROVED("Đã duyệt"),
        REJECTED("Từ chối"),
        CANCELLED("Đã hủy");

        private final String vietnameseName;

        LeaveStatus(String vietnameseName) {
            this.vietnameseName = vietnameseName;
        }

        public String getVietnameseName() {
            return vietnameseName;
        }
    }

    // Constructors
    public LeaveApplication() {
        this.status = LeaveStatus.PENDING;
    }

    public LeaveApplication(int leaveApplicationId, String leaveType, int userId,
                            Date startDate, Date endDate, String reason,
                            LeaveStatus status, boolean isPaid) {
        setLeaveApplicationId(leaveApplicationId);
        setLeaveType(leaveType);
        setUserId(userId);
        setStartDate(startDate);
        setEndDate(endDate);
        setReason(reason);
        setStatus(status);
        setPaid(isPaid);
    }

    // Getters and Setters with validation
    public int getLeaveApplicationId() {
        return leaveApplicationId;
    }

    public void setLeaveApplicationId(int leaveApplicationId) {
        if (leaveApplicationId <= 0) {
            throw new IllegalArgumentException("Leave application ID must be positive");
        }
        this.leaveApplicationId = leaveApplicationId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        if (leaveType == null || leaveType.trim().isEmpty()) {
            throw new IllegalArgumentException("Leave type cannot be null or empty");
        }
        this.leaveType = leaveType.trim();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        Objects.requireNonNull(startDate, "Start date cannot be null");
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        Objects.requireNonNull(endDate, "End date cannot be null");
        if (startDate != null && endDate.before(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be null or empty");
        }
        this.reason = reason.trim();
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status != null ? status : LeaveStatus.PENDING;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    // Business logic methods
    public boolean isApproved() {
        return status == LeaveStatus.APPROVED;
    }

    public long getLeaveDays() {
        if (startDate == null || endDate == null) {
            return 0;
        }
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS) + 1;
    }

    // toString method
    @Override
    public String toString() {
        return "LeaveApplication{" +
                "leaveApplicationId=" + leaveApplicationId +
                ", leaveType='" + leaveType + '\'' +
                ", userId=" + userId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                ", isPaid=" + isPaid +
                '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaveApplication that = (LeaveApplication) o;
        return leaveApplicationId == that.leaveApplicationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(leaveApplicationId);
    }
}