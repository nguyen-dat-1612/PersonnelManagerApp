package com.managerapp.personnelmanagerapp.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "notification_recipients")
public class NotificationRecipientEntity {
    @PrimaryKey
    @SerializedName("id")
    private long id;
    @SerializedName("readStatus")
    private boolean readStatus;
    @SerializedName("title")
    private String title;
    @SerializedName("sendDate")
    private String sendDate;

    public NotificationRecipientEntity(long id, boolean readStatus, String title, String sendDate) {
        this.id = id;
        this.readStatus = readStatus;
        this.title = title;
        this.sendDate = sendDate;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "NotificationRecipientEntity{" +
                "id=" + id +
                ", readStatus=" + readStatus +
                ", title='" + title + '\'' +
                ", sendDate='" + sendDate + '\'' +
                '}';
    }
}