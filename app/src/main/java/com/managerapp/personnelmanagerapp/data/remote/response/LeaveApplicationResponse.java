package com.managerapp.personnelmanagerapp.data.remote.response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LeaveApplicationResponse {
    private long id;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String formStatusEnum;
    private User user;
    private String leaveTypeName;

    public static class User {
        private long id;
        private String fullName;

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    public String getStartDate() {
        if (startDate == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("'Ngày' dd 'tháng' MM 'năm' yyyy", new Locale("vi", "VN"));
        return sdf.format(startDate);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        if (endDate == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("'Ngày' dd 'tháng' MM 'năm' yyyy", new Locale("vi", "VN"));
        return sdf.format(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFormStatusEnumColor() {
        if (formStatusEnum == null) return "";
        switch (formStatusEnum) {
            case "PENDING":
                return "🔵 Đang chờ";
            case "REJECTED":
                return "🔴 Từ chối";
            case "APPROVED":
                return "🟢 Đã duyệt";
            default:
                return "Không có";
        }
    }
    public String getFormStatusEnum() {
        return formStatusEnum;
    }

    public void setFormStatusEnum(String formStatusEnum) {
        this.formStatusEnum = formStatusEnum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    @Override
    public String toString() {
        return "LeaveApplicationResponse{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", reason='" + reason + '\'' +
                ", formStatusEnum='" + formStatusEnum + '\'' +
                ", user=" + user +
                ", leaveTypeName='" + leaveTypeName + '\'' +
                '}';
    }
}