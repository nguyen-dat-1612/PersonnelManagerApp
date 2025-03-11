package com.managerapp.personnelmanagerapp.domain.model;

public class Notification {
    private String id;
    private String title;
    private String content;
    private String sendDate;
    private int senderId;
    private String status;

    public Notification(String id, int senderId, String sendDate, String content, String title, String status) {
        this.id = id;
        this.senderId = senderId;
        this.sendDate = sendDate;
        this.content = content;
        this.title = title;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
