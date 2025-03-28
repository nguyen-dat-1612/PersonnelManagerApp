package com.managerapp.personnelmanagerapp.ui.state;

import androidx.annotation.NonNull;
import com.managerapp.personnelmanagerapp.domain.model.DataItem;
import com.managerapp.personnelmanagerapp.domain.model.User;

import java.util.List;

public sealed interface ProfileInfoState {
    final class Loading implements ProfileInfoState {
        private static final ProfileInfoState.Loading INSTANCE = new ProfileInfoState.Loading();
        private Loading() {}

        public static ProfileInfoState.Loading getInstance() {
            return INSTANCE;
        }
    }
    final class Success implements ProfileInfoState {
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
    final class Error implements ProfileInfoState {
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
    final class Empty implements ProfileInfoState {}
}
