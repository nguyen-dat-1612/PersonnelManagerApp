package com.managerapp.personnelmanagerapp.data.remote.response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ContractResponse {
    private int id;
    private Date startDate;
    private Date endDate;
    private double basicSalary;
    private String clause;
    private String contractStatusEnum;
    private String contractTypeName ;
    private User user;
    private User signer;
    private String positionName;
    private String jobGradeName;

    public static class User {
        private long id;
        private String fullName;

        public User(long id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }

    public ContractResponse(int id, String jobGradeName, String positionName, User signer, User user, String contractTypeName, String contractStatusEnum, String clause, double basicSalary, Date endDate, Date startDate) {
        this.id = id;
        this.jobGradeName = jobGradeName;
        this.positionName = positionName;
        this.signer = signer;
        this.user = user;
        this.contractTypeName = contractTypeName;
        this.contractStatusEnum = contractStatusEnum;
        this.clause = clause;
        this.basicSalary = basicSalary;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause = clause;
    }

    public String getContractStatusEnum() {
        if (contractStatusEnum == null) return "";
        switch (contractStatusEnum) {
            case "PENDING":
                return "🔵 Đang chờ";
            case "EXPIRED":
                return "⚫ Hết hạn";
            case "TERMINATED":
                return "🔴 Đã hủy";
            case "RENEWED":
                return "🟢 Đã gia hạn";
            default:
                return "";
        }
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSigner() {
        return signer;
    }

    public void setSigner(User signer) {
        this.signer = signer;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getJobGradeName() {
        return jobGradeName;
    }

    public void setJobGradeName(String jobGradeName) {
        this.jobGradeName = jobGradeName;
    }

    @Override
    public String toString() {
        return "ContractResponse{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", basicSalary=" + basicSalary +
                ", clause='" + clause + '\'' +
                ", contractStatusEnum='" + contractStatusEnum + '\'' +
                ", contractTypeName='" + contractTypeName + '\'' +
                ", user=" + user +
                ", signer=" + signer +
                ", positionName='" + positionName + '\'' +
                ", jobGradeName='" + jobGradeName + '\'' +
                '}';
    }
}

