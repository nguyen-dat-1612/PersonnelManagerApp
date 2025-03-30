package com.managerapp.personnelmanagerapp.ui.state;

import androidx.annotation.NonNull;

public sealed interface ChangePasswordState {
    final class Loading implements ChangePasswordState {
        private static final Loading INSTANCE = new ChangePasswordState.Loading();

        public Loading() {}

        public static Loading getInstance() {
            return INSTANCE;
        }
    }
    final class Success implements ChangePasswordState {
        @NonNull
        private final String sucessMessage;

        public Success(@NonNull String sucessMessage) {
            this.sucessMessage = sucessMessage;
        }

        @NonNull
        public String getSucessMessage() {
            return sucessMessage;
        }
    }
    final class Error implements ChangePasswordState {
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
