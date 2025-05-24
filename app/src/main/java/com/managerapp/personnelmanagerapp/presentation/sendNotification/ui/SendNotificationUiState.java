package com.managerapp.personnelmanagerapp.presentation.sendNotification.ui;

import com.managerapp.personnelmanagerapp.domain.model.UserSummary;

import java.util.Collections;
import java.util.List;

public class SendNotificationUiState {
    public final boolean isLoading;
    public final List<UserSummary> userSummaryList;
    public final String errorMessage;


    public SendNotificationUiState(boolean isLoading, List<UserSummary> userSummaryList, String errorMessage) {
        this.isLoading = isLoading;
        this.userSummaryList = userSummaryList;
        this.errorMessage = errorMessage;
    }

    public static SendNotificationUiState loading() {
        return new SendNotificationUiState(true, Collections.emptyList(), null);
    }

    public static SendNotificationUiState success(List<UserSummary> data) {
        return new SendNotificationUiState(false, data, null);
    }

    public static SendNotificationUiState error(String message) {
        return new SendNotificationUiState(false, Collections.emptyList(), message);
    }
}
