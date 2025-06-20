package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;
import com.managerapp.personnelmanagerapp.data.mapper.UserSummaryMapper;
import com.managerapp.personnelmanagerapp.domain.model.ContractStatusEnum;

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
    private ContractStatusEnum contractStatusEnum;
    @SerializedName("contractTypeName")
    private String contractTypeName ;
    @SerializedName("user")
    private UserSummaryResponse user;
    @SerializedName("signer")
    private UserSummaryResponse signer;
    @SerializedName("positionName")
    private String positionName;
    @SerializedName("departmentName")
    private String departmentName;
    @SerializedName("jobGradeCoefficient")
    private double jobGradeCoefficient;

    public ContractResponse(int id, String jobGradeName, String positionName, UserSummaryResponse signer, UserSummaryResponse user, String contractTypeName, ContractStatusEnum contractStatusEnum, String clause, double basicSalary, Date endDate, Date startDate) {
        this.id = id;
        this.departmentName = jobGradeName;
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
    public String getContractStatusEnumUI() {
        if (contractStatusEnum == null) return "";
        switch (contractStatusEnum) {
            case PENDING:
                return "🔵 Đang chờ";
            case EXPIRED:
                return "⚫ Hết hạn";
            case TERMINATED:
                return "🔴 Đã hủy";
            case RENEWED:
                return "🟢 Đã gia hạn";
            case SIGNED_PENDING_EFFECTIVE:
                return "🟡 Đã ký";
            case ACTIVE:
                return "🟢 Đang hiệu lực";
            default:
                return "";
        }
    }
    public ContractStatusEnum getContractStatusEnum() {
        return contractStatusEnum;
    }
    public String getContractTypeName() {
        return contractTypeName;
    }
    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }
    public UserSummaryResponse getUser() {
        return user;
    }
    public void setUser(UserSummaryResponse user) {
        this.user = user;
    }
    public UserSummaryResponse getSigner() {
        return signer;
    }
    public String getPositionName() {
        return positionName;
    }
    public String getDepartmentName() {
        return departmentName;
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
                ", jobGradeName='" + departmentName + '\'' +
                '}';
    }
}

