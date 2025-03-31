package com.managerapp.personnelmanagerapp.ui.state;

import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;

import java.util.List;

public sealed interface NotificationState {
    final class Loading implements NotificationState {}
    final class Success implements NotificationState {
        public final List<NotificationRecipient> notifications;

        public Success(List<NotificationRecipient> notifications) {
            this.notifications = notifications;
        }
    }
    final class Error implements NotificationState {
        public final String message;

        public Error(String message) {
            this.message = message;
        }
    }
    final class Empty implements NotificationState {}
}
