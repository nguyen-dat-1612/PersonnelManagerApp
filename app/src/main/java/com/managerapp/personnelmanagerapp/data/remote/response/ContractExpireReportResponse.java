package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;
import com.managerapp.personnelmanagerapp.domain.model.ContractStatusEnum;

public class ContractExpireReportResponse {

    @SerializedName("stt")
    private int stt;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("email")
    private String email;
    @SerializedName("departmentName")
    private String departmentName;
    @SerializedName("positionName")
    private String positionName;
    @SerializedName("contractTypeName")
    private String contractTypeName;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("remainingDays")
    private int remainingDays;
    @SerializedName("contractStatus")
    private ContractStatusEnum contractStatus;


    public ContractExpireReportResponse(int stt, String fullName, String email, String departmentName, String positionName, String contractTypeName, String endDate, ContractStatusEnum contractStatus, int remainingDays) {
        this.stt = stt;
        this.fullName = fullName;
        this.email = email;
        this.departmentName = departmentName;
        this.positionName = positionName;
        this.contractTypeName = contractTypeName;
        this.endDate = endDate;
        this.contractStatus = contractStatus;
        this.remainingDays = remainingDays;
    }

    public int getStt() {
        return stt;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public ContractStatusEnum getContractStatus() {
        return contractStatus;
    }
}
