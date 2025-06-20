package com.managerapp.personnelmanagerapp.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LeaveApplication implements Serializable {

    private long id;
    private Date startDate;
    private Date endDate;
    private String reason;
    private FormStatusEnum formStatusEnum;
    private UserSummary user;
    private UserSummary signer;
    private String leaveTypeName;

    public LeaveApplication() {
    }

    public LeaveApplication(long id, Date startDate, Date endDate, String reason,
                            FormStatusEnum formStatusEnum, UserSummary user,
                            UserSummary signer, String leaveTypeName) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.formStatusEnum = formStatusEnum;
        this.user = user;
        this.signer = signer;
        this.leaveTypeName = leaveTypeName;
    }

    // Builder Pattern
    public static class Builder {
        private long id;
        private Date startDate;
        private Date endDate;
        private String reason;
        private FormStatusEnum formStatusEnum;
        private UserSummary user;
        private UserSummary signer;
        private String leaveTypeName;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder formStatusEnum(FormStatusEnum formStatusEnum) {
            this.formStatusEnum = formStatusEnum;
            return this;
        }

        public Builder user(UserSummary user) {
            this.user = user;
            return this;
        }

        public Builder signer(UserSummary signer) {
            this.signer = signer;
            return this;
        }

        public Builder leaveTypeName(String leaveTypeName) {
            this.leaveTypeName = leaveTypeName;
            return this;
        }

        public LeaveApplication build() {
            return new LeaveApplication(id, startDate, endDate, reason, formStatusEnum, user, signer, leaveTypeName);
        }
    }

    // Getters và Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public FormStatusEnum getFormStatusEnum() {
        return formStatusEnum;
    }

    public void setFormStatusEnum(FormStatusEnum formStatusEnum) {
        this.formStatusEnum = formStatusEnum;
    }

    public UserSummary getUser() {
        return user;
    }

    public void setUser(UserSummary user) {
        this.user = user;
    }

    public UserSummary getSigner() {
        return signer;
    }

    public void setSigner(UserSummary signer) {
        this.signer = signer;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    // Logic tính số ngày nghỉ
    public long getLeaveDays() {
        if (startDate == null || endDate == null) return 0;
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS) + 1;
    }

    @Override
    public String toString() {
        return "LeaveApplication{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", reason='" + reason + '\'' +
                ", formStatusEnum=" + formStatusEnum +
                ", user=" + user +
                ", signer=" + signer +
                ", leaveTypeName='" + leaveTypeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeaveApplication)) return false;
        LeaveApplication that = (LeaveApplication) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
