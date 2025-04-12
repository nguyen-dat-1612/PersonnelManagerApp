package com.managerapp.personnelmanagerapp.ui.state;

import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientEntity;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;

import java.util.List;

public sealed interface NotificationRecipientState {
    final class Loading implements NotificationRecipientState {}
    final class Success implements NotificationRecipientState {
        public final List<NotificationRecipientEntity> notifications;

        public Success(List<NotificationRecipientEntity> notifications) {
            this.notifications = notifications;
        }

        public List<NotificationRecipientEntity> getNotifications() {
            return notifications;
        }
    }
    final class Error implements NotificationRecipientState {
        public final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
    final class Empty implements NotificationRecipientState {}
}
