package com.managerapp.personnelmanagerapp.ui.state;

import androidx.annotation.NonNull;

import com.managerapp.personnelmanagerapp.domain.model.User;

public sealed interface MainState {
    final class Loading implements MainState {
        private static final MainState.Loading INSTANCE = new MainState.Loading();
        private Loading() {}

        public static MainState.Loading getInstance() {
            return INSTANCE;
        }
    }
    final class Success implements MainState {
        @NonNull
        public final User user;

        public Success(@NonNull User user) {
            this.user = user;
        }

        @NonNull
        public User getUser() {
            return user;
        }
    }
    final class Error implements MainState {
        @NonNull
        public final String message;


        public Error(@NonNull String message) {
            this.message = message;
        }
        @NonNull
        public String getMessage() {
            return message;
        }
    }
}
