package com.managerapp.personnelmanagerapp.domain.model;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;

public class Notification {
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
    private Sender sender;

    public class Sender {
        @SerializedName("id")
        private int id;
        @SerializedName("fullName")
        private String fullName;

        public Sender(int id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }

        public int getId() {
            return id;
        }

        public String getFullName() {
            return fullName;
        }
    }


    public Notification(long id, String title, String content, List<String> attached, String sendDate, Sender sender) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.attached = attached;
        this.sendDate = sendDate;
        this.sender = sender;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAttached() {
        return attached;
    }

    public String getContent() {
        return content;
    }

    public String getSendDate() {
        return sendDate;
    }

    public Sender getSender() {
        return sender;
    }

    // toString method for debugging/logging
    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}
