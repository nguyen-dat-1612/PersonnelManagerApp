package com.managerapp.personnelmanagerapp.domain.model;

import java.time.LocalDateTime;

public class Notification {
    private String id;          // Primary key (e.g., TB001, TB002,...)
    private String title;       // Notification title (not null, max 255 chars)
    private String content;     // Notification content (not null)
    private LocalDateTime sendDate; // Date and time when notification was sent (not null)

    // Constructors
    public Notification() {
    }

    public Notification(String id, String title, String content, LocalDateTime sendDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.sendDate = sendDate;
    }

    // Getters and Setters
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
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (title.length() > 255) {
            throw new IllegalArgumentException("Title cannot exceed 255 characters");
        }
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        this.content = content;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        if (sendDate == null) {
            throw new IllegalArgumentException("Send date cannot be null");
        }
        this.sendDate = sendDate;
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
