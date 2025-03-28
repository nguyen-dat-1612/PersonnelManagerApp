package com.managerapp.personnelmanagerapp.ui.state;

import androidx.annotation.NonNull;

public sealed interface FeedbackState permits FeedbackState.Loading, FeedbackState.Success, FeedbackState.Error {

    final class Loading implements FeedbackState {
        private static final Loading INSTANCE = new Loading();
        private Loading() {}

        public static Loading getInstance() {
            return INSTANCE;
        }
    }

    final class Success implements FeedbackState {
        @NonNull
        private final String successMessage;

        public Success(@NonNull String successMessage) {
            this.successMessage = successMessage;
        }

        @NonNull
        public String getSuccessMessage() {
            return successMessage;
        }
    }

    final class Error implements FeedbackState {
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
