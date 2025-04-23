package com.managerapp.personnelmanagerapp.data.remote.response;

import java.time.LocalDateTime;

public class FeedbackResponse {
    private long id;
    private String title;
    private String content;
    private String sendDate;

    private UserSummaryResponse sender;

    public static class UserSummaryResponse {
        private long id;
        private String fullName;
    }
}
