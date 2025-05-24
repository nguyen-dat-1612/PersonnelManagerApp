package com.managerapp.personnelmanagerapp.presentation.auth.state;

import androidx.annotation.NonNull;

public sealed interface ForgotPasswordState permits
        ForgotPasswordState.Loading,
        ForgotPasswordState.EmailSent,
        ForgotPasswordState.OtpVerified,
        ForgotPasswordState.PasswordResetSuccess,
        ForgotPasswordState.Error {

    final class Loading implements ForgotPasswordState {
        private static final Loading INSTANCE = new Loading();

        private Loading() {}

        public static ForgotPasswordState.Loading getInstance() {
            return INSTANCE;
        }
    }

    final class EmailSent implements ForgotPasswordState {
        @NonNull
        private final String email;

        public EmailSent(@NonNull String email) {
            this.email = email;
        }

        @NonNull
        public String getEmail() {
            return email;
        }
    }

    final class OtpVerified implements ForgotPasswordState {}

    final class PasswordResetSuccess implements ForgotPasswordState {}

    final class Error implements ForgotPasswordState {
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

