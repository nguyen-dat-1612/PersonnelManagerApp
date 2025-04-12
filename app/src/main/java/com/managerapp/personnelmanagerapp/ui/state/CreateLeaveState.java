package com.managerapp.personnelmanagerapp.ui.state;

import androidx.annotation.NonNull;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;

public sealed interface CreateLeaveState {
    final class Loading implements CreateLeaveState {
        private static final Loading INSTANCE = new CreateLeaveState.Loading();

        public Loading() {}

        public static Loading getInstance() {
            return INSTANCE;
        }
    }
    final class Success implements CreateLeaveState {
        @NonNull
        private final LeaveApplicationResponse leaveApplicationResponse;


        public Success(@NonNull LeaveApplicationResponse leaveApplicationResponse) {
            this.leaveApplicationResponse = leaveApplicationResponse;
        }

        @NonNull
        public LeaveApplicationResponse getLeaveApplicationResponse() {
            return leaveApplicationResponse;
        }
    }
    final class Error implements CreateLeaveState {
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
