package com.managerapp.personnelmanagerapp.data.remote.response;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AssignmentResponse {
    private AssignmentId id;
    private String type;
    private String content;
    private Date date;
    private UserSummaryResponse userSummaryResponse;

    public AssignmentResponse() {
    }

    public AssignmentResponse(AssignmentId id, String type, String content, Date date, UserSummaryResponse userSummaryResponse) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.date = date;
        this.userSummaryResponse = userSummaryResponse;
    }

    public AssignmentId getId() {
        return id;
    }

    public void setId(AssignmentId id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("'Ngày' dd 'tháng' MM 'năm' yyyy", new Locale("vi", "VN"));
        return sdf.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserSummaryResponse getUserSummaryResponse() {
        return userSummaryResponse;
    }

    public void setUserSummaryResponse(UserSummaryResponse userSummaryResponse) {
        this.userSummaryResponse = userSummaryResponse;
    }

    // Nested class AssignmentId
    public static class AssignmentId implements Serializable {
        private long userId;
        private String decisionId;

        public AssignmentId() {
        }

        public AssignmentId(long userId, String decisionId) {
            this.userId = userId;
            this.decisionId = decisionId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getDecisionId() {
            return decisionId;
        }

        public void setDecisionId(String decisionId) {
            this.decisionId = decisionId;
        }
    }

    // Nested class UserSummaryResponse
    public static class UserSummaryResponse {
        private long id;
        private String fullName;

        public UserSummaryResponse() {
        }

        public UserSummaryResponse(long id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }

    @Override
    public String toString() {
        return "AssignmentResponse{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", userSummaryResponse=" + userSummaryResponse +
                '}';
    }
}
