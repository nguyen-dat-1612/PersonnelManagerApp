package com.managerapp.personnelmanagerapp.domain.model;


public class NotificationRecipient {
    private long id;
    private boolean readStatus;
    private String title;
    private String sendDate;

    public NotificationRecipient(long id, boolean readStatus, String title, String sendDate) {
        this.id = id;
        this.readStatus = readStatus;
        this.title = title;
        this.sendDate = sendDate;
    }

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
}
