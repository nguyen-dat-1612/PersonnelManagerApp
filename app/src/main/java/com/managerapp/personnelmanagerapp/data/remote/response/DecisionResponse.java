package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;
import com.managerapp.personnelmanagerapp.domain.model.DecisionEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class DecisionResponse implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("attachment")
    private String attachment;

    @SerializedName("content")
    private String content;

    @SerializedName("value")
    private double value;

    @SerializedName("type")
    private DecisionEnum type;

    @SerializedName("date")
    private String date;

    @SerializedName("signer")
    private UserSummaryResponse signer;

    @SerializedName("user")
    private UserSummaryResponse user;

    @SerializedName("seniorityAllowanceRule")
    private SeniorityAllowanceRuleResponse seniorityAllowanceRule;

    @SerializedName("salaryPromotion")
    private SalaryPromotionResponse salaryPromotion;

    @SerializedName("position")
    private PositionResponse position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public DecisionEnum getType() {
        return type;
    }

    public void setType(DecisionEnum type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserSummaryResponse getSigner() {
        return signer;
    }

    public void setSigner(UserSummaryResponse signer) {
        this.signer = signer;
    }

    public UserSummaryResponse getUser() {
        return user;
    }

    public void setUser(UserSummaryResponse user) {
        this.user = user;
    }

    public SeniorityAllowanceRuleResponse getSeniorityAllowanceRule() {
        return seniorityAllowanceRule;
    }

    public void setSeniorityAllowanceRule(SeniorityAllowanceRuleResponse seniorityAllowanceRule) {
        this.seniorityAllowanceRule = seniorityAllowanceRule;
    }

    public SalaryPromotionResponse getSalaryPromotion() {
        return salaryPromotion;
    }

    public void setSalaryPromotion(SalaryPromotionResponse salaryPromotion) {
        this.salaryPromotion = salaryPromotion;
    }

    public PositionResponse getPosition() {
        return position;
    }

    public void setPosition(PositionResponse position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DecisionResponse that = (DecisionResponse) o;
        return Double.compare(value, that.value) == 0 && Objects.equals(id, that.id) && Objects.equals(attachment, that.attachment) && Objects.equals(content, that.content) && Objects.equals(type, that.type) && Objects.equals(date, that.date) && Objects.equals(signer, that.signer) && Objects.equals(user, that.user) && Objects.equals(seniorityAllowanceRule, that.seniorityAllowanceRule) && Objects.equals(position, that.position) && Objects.equals(salaryPromotion, that.salaryPromotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attachment, content, value, type, date, signer, user, seniorityAllowanceRule, position, salaryPromotion);
    }
}
