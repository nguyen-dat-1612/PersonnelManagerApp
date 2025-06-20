package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NotificationResponse {
    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("attached")
    private List<String> attached;
    @SerializedName("sendDate")
    private String sendDate;

    @SerializedName("sender")
    private UserSummaryResponse sender;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getAttached() {
        return attached;
    }

    public String getSendDate() {
        return sendDate;
    }

    public UserSummaryResponse getSender() {
        return sender;
    }
}

