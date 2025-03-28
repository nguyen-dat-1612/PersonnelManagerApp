package com.managerapp.personnelmanagerapp.ui.state;

import androidx.annotation.NonNull;

public sealed interface LoginState permits LoginState.Loading, LoginState.Success, LoginState.Error {

    final class Loading implements LoginState {
        private static final Loading INSTANCE = new Loading();

        private Loading() {};

        public static LoginState.Loading getInstance() {
            return INSTANCE;
        }
    }

    final class Success implements LoginState {
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

    final class Error implements LoginState {
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


