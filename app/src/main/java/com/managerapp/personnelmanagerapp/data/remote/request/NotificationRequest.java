package com.managerapp.personnelmanagerapp.data.remote.request;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotificationRequest {
    @SerializedName("title")
    @NotNull
    private String title;

    @SerializedName("content")
    @NotNull
    private String content;
    @SerializedName("userId")
    private Long userId;
    @SerializedName("recipientText")
    private String recipientText;

    @SerializedName("attached")

    private List<String> attached;

    @SerializedName("receiverIds")
    private List<Long> receiverIds;

    @SerializedName("positionIds")
    private List<String> positionIds;

    @SerializedName("departmentIds")
    private List<String> departmentIds;


    public NotificationRequest(String title, String content, Long userId, String recipientText, List<String> attached, List<Long> receiverIds, List<String> positionIds, List<String> departmentIds) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.recipientText = recipientText;
        this.attached = attached;
        this.receiverIds = receiverIds;
        this.positionIds = positionIds;
        this.departmentIds = departmentIds;
    }
}


