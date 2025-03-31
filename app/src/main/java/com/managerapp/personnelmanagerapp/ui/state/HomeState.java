package com.managerapp.personnelmanagerapp.ui.state;

import androidx.annotation.NonNull;

import com.managerapp.personnelmanagerapp.domain.model.User;

public sealed interface HomeState {
    final class Loading implements HomeState {
        private static final HomeState.Loading INSTANCE = new HomeState.Loading();
        private Loading() {}

        public static HomeState.Loading getInstance() {
            return INSTANCE;
        }
    }
    final class Success implements HomeState {
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
    final class Error implements HomeState {
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
