package com.managerapp.personnelmanagerapp.data.remote.request;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.managerapp.personnelmanagerapp.domain.model.DecisionEnum;

public class DecisionRequest {
    @SerializedName("id")
    @NonNull
    private String id;

    @SerializedName("value")
    private double value;

    @SerializedName("content")
    private String content;

    @SerializedName("type")
    @NonNull
    private DecisionEnum type;

    @SerializedName("date")
    @NonNull
    private String date;

    @SerializedName("effectiveDate")
    @NonNull
    private String effectiveDate;

    @SerializedName("userId")
    private long userId;

    @SerializedName("salaryPromotionId")
    private Integer salaryPromotionId;

    @SerializedName("positionId")
    private String positionId;

    @SerializedName("seniorityAllowanceRuleId")
    private Integer seniorityAllowanceRuleId;

    // Constructors
    public DecisionRequest() {
    }

    public DecisionRequest(@NonNull String id, double value, String content, @NonNull DecisionEnum type,
                           @NonNull String date, @NonNull String effectiveDate, long userId,
                           Integer salaryPromotionId, String positionId, Integer seniorityAllowanceRuleId) {
        this.id = id;
        this.value = value;
        this.content = content;
        this.type = type;
        this.date = date;
        this.effectiveDate = effectiveDate;
        this.userId = userId;
        this.salaryPromotionId = salaryPromotionId;
        this.positionId = positionId;
        this.seniorityAllowanceRuleId = seniorityAllowanceRuleId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NonNull
    public DecisionEnum getType() {
        return type;
    }

    public void setType(@NonNull DecisionEnum type) {
        this.type = type;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(@NonNull String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getSalaryPromotionId() {
        return salaryPromotionId;
    }

    public void setSalaryPromotionId(Integer salaryPromotionId) {
        this.salaryPromotionId = salaryPromotionId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public Integer getSeniorityAllowanceRuleId() {
        return seniorityAllowanceRuleId;
    }

    public void setSeniorityAllowanceRuleId(Integer seniorityAllowanceRuleId) {
        this.seniorityAllowanceRuleId = seniorityAllowanceRuleId;
    }
}

