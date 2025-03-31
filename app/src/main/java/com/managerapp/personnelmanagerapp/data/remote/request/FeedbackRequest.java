package com.managerapp.personnelmanagerapp.data.remote.request;

import java.util.Date;

public class FeedbackRequest {
    private int userId;      // Mã người dùng phản hồi
    private String title;    // Tiêu đề phản hồi
    private String content;  // Nội dung phản hồi
    private Date sendDate;   // Thời gian phản hồi

    // Constructors
    public FeedbackRequest() {
    }

    public FeedbackRequest(int userId, String title, String content, Date sendDate) {
        setUserId(userId);
        setTitle(title);
        setContent(content);
        setSendDate(sendDate);
    }

    // Getters and Setters
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

    // toString for debugging/logging
    @Override
    public String toString() {
        return "FeedbackRequest{" +
                "userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}
