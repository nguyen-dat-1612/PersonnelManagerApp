package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;
import com.managerapp.personnelmanagerapp.domain.model.Notification;

import java.util.List;

public class NotificationResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Notification> data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Notification> getData() {
        return data;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<Notification> data) {
        this.data = data;
    }
}
