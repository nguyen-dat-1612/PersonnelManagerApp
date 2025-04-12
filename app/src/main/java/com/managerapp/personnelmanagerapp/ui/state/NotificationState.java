package com.managerapp.personnelmanagerapp.ui.state;

import androidx.annotation.NonNull;

import com.managerapp.personnelmanagerapp.domain.model.Notification;

public sealed interface NotificationState {
    final class Loading implements NotificationState {
        private static final NotificationState.Loading INSTANCE = new NotificationState.Loading();

        private Loading() {};

        public static NotificationState.Loading getInstance() {
            return INSTANCE;
        }
    }

    final class Success implements NotificationState {
        @NonNull
        private final Notification notification;

        public Success(@NonNull Notification notification) {
            this.notification = notification;
        }

        @NonNull
        public Notification getNotification() {
            return notification;
        }
    }

    final class Error implements NotificationState {
        @NonNull
        private final String errorMessage;

        public Error(@NonNull String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @NonNull
        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
