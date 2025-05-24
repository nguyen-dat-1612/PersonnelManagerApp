package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class SeniorityAllowanceRuleResponse implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("minService")
    private int minService;

    @SerializedName("seniorityPercentage")
    private double seniorityPercentage;

    @SerializedName("seniorityLeaveDay")
    private int seniorityLeaveDay;

    @SerializedName("effectiveDate")
    private String effectiveDate;

    @SerializedName("expiryDate")
    private String expiryDate;

    @SerializedName("description")
    private String description;

    @SerializedName("signer")
    private UserSummaryResponse signer;

    public int getId() {
        return id;
    }

    public int getMinService() {
        return minService;
    }

    public double getSeniorityPercentage() {
        return seniorityPercentage;
    }

    public int getSeniorityLeaveDay() {
        return seniorityLeaveDay;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getDescription() {
        return description;
    }

    public UserSummaryResponse getSigner() {
        return signer;
    }
}
