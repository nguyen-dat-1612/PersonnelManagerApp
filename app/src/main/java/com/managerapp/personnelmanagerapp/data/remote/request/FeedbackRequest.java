package com.managerapp.personnelmanagerapp.data.remote.request;

import java.util.Date;

public class FeedbackRequest {
    private String title;    // Tiêu đề phản hồi
    private String content;
    private long userId;

    // Constructors
    public FeedbackRequest() {
    }

    public FeedbackRequest(String title, String content) {
        setTitle(title);
        setContent(content);
    }
    public FeedbackRequest(String title, String content, long userId) {
        setTitle(title);
        setContent(content);
        setUserId(userId);
    }

    // Getters and Setters
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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


    // toString for debugging/logging
    @Override
    public String toString() {
        return "FeedbackRequest{" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
