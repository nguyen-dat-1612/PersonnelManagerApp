package com.managerapp.personnelmanagerapp.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Notification {
    private long id;
    private String title;
    private String content;
    private List<String> attached;
    private String sendDate;
    private UserSummary sender;

    public Notification(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Notification(long id, String title, String content, List<String> attached, String sendDate, UserSummary sender) {
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

    public UserSummary getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }

    public static class Builder {
        private long id;
        private String title;
        private String content;
        private List<String> attached;
        private String sendDate;
        private UserSummary sender;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder attached(List<String> attached) {
            this.attached = attached;
            return this;
        }

        public Builder sendDate(String sendDate) {
            this.sendDate = sendDate;
            return this;
        }

        public Builder sender(UserSummary sender) {
            this.sender = sender;
            return this;
        }

        public Notification build() {
            return new Notification(id, title, content, attached, sendDate, sender);
        }
    }
}
