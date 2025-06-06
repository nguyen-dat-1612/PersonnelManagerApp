package com.managerapp.personnelmanagerapp.data.remote.response;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class LeaveApplicationResponse implements Serializable {
    private long id;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String formStatusEnum;
    private User user;
    private String leaveTypeName;

    public LeaveApplicationResponse(long id, Date startDate, Date endDate, String reason, String formStatusEnum, User user, String leaveTypeName) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.formStatusEnum = formStatusEnum;
        this.user = user;
        this.leaveTypeName = leaveTypeName;
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LeaveApplicationResponse that = (LeaveApplicationResponse) o;
        return id == that.id && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(reason, that.reason) && Objects.equals(formStatusEnum, that.formStatusEnum) && Objects.equals(user, that.user) && Objects.equals(leaveTypeName, that.leaveTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, reason, formStatusEnum, user, leaveTypeName);
    }
}