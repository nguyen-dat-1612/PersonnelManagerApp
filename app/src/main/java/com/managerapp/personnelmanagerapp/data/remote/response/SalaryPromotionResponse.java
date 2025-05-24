package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class SalaryPromotionResponse implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private String date;

    @SerializedName("status")
    private String status;

    @SerializedName("note")
    private String note;

    @SerializedName("reason")
    private String reason;

    @SerializedName("userName")
    private String userName;

    @SerializedName("signerName")
    private String signerName;

    @SerializedName("currentJobGradeName")
    private String currentJobGradeName;

    @SerializedName("requestJobGradeName")
    private String requestJobGradeName;

    @SerializedName("requestJobGradeValue")
    private double requestJobGradeValue;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public String getReason() {
        return reason;
    }

    public String getUserName() {
        return userName;
    }

    public String getSignerName() {
        return signerName;
    }

    public String getCurrentJobGradeName() {
        return currentJobGradeName;
    }

    public String getRequestJobGradeName() {
        return requestJobGradeName;
    }

    public double getRequestJobGradeValue() {
        return requestJobGradeValue;
    }
}
