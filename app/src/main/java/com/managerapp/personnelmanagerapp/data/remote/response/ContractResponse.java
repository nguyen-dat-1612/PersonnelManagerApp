package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ContractResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("startDate")
    private Date startDate;
    @SerializedName("endDate")
    private Date endDate;
    @SerializedName("basicSalary")
    private double basicSalary;
    @SerializedName("clause")
    private String clause;
    @SerializedName("contractStatusEnum")
    private final String contractStatusEnum;
    @SerializedName("contractTypeName")
    private String contractTypeName ;
    @SerializedName("user")
    private User user;
    @SerializedName("signer")
    private User signer;
    @SerializedName("positionName")
    private String positionName;
    @SerializedName("jobGradeName")
    private String jobGradeName;

    @SerializedName("jobGradeCoefficient")
    private double jobGradeCoefficient;

    public static class User {
        @SerializedName("id")
        private long id;
        @SerializedName("fullName")
        private String fullName;
        @SerializedName("numberCCCD")
        private String numberCCCD;
        @SerializedName("dob")
        private String dateOfBirth;
        @SerializedName("phoneNumber")
        private String phoneNumber;
        @SerializedName("nationality")
        private String nationality;
        @SerializedName("email")
        private String email;
        @SerializedName("address")
        private String address;

        public User(long id, String fullName, String numberCCCD, String dateOfBirth, String phoneNumber, String nationality, String email, String address) {
            this.id = id;
            this.fullName = fullName;
            this.numberCCCD = numberCCCD;
            this.dateOfBirth = dateOfBirth;
            this.phoneNumber = phoneNumber;
            this.nationality = nationality;
            this.email = email;
            this.address = address;
        }

        public long getId() {
            return id;
        }

        public String getFullName() {
            return fullName;
        }

        public String getNumberCCCD() {
            return numberCCCD;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getNationality() {
            return nationality;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
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
            case "SIGNED_PENDING_EFFECTIVE":
                return "🟡 Đã ký";
            case "ACTIVE":
                return "🟢 Đang hiệu lực";
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

    public String getPositionName() {
        return positionName;
    }

    public String getJobGradeName() {
        return jobGradeName;
    }

    public double getJobGradeCoefficient() {
        return jobGradeCoefficient;
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

