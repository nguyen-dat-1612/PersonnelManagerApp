package com.managerapp.personnelmanagerapp.domain.model;

import java.util.Date;
import java.util.Objects;

public class Feedback {
    private String id;          // Mã phản hồi
    private int userId;         // Mã người dùng phản hồi
    private String title;       // Tiêu đề phản hồi
    private String content;     // Nội dung phản hồi
    private Date sendDate;      // Thời gian phản hồi

    // Constructors
    public Feedback() {
    }

    public Feedback(String id, int userId, String title, String content, Date sendDate) {
        setId(id);
        setUserId(userId);
        setTitle(title);
        setContent(content);
        setSendDate(sendDate);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Feedback ID cannot be null or empty");
        }
        this.id = id.trim();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("UserEntity ID must be positive");
        }
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        this.content = content.trim();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        if (sendDate == null) {
            throw new IllegalArgumentException("Send date cannot be null");
        }
        this.sendDate = sendDate;
    }

    // toString method for debugging/logging
    @Override
    public String toString() {
        return "Feedback{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return id.equals(feedback.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}