package com.managerapp.personnelmanagerapp.ui.state;

import androidx.annotation.NonNull;

import com.managerapp.personnelmanagerapp.domain.model.LeaveType;

import java.util.List;

public sealed interface LeaveTypesState {
    final class Loading implements LeaveTypesState {
        private static final Loading INSTANCE = new LeaveTypesState.Loading();

        public Loading() {}

        public static Loading getInstance() {
            return INSTANCE;
        }
    }
    final class Success implements LeaveTypesState {
        @NonNull
        private final List<LeaveType> leaveTypes;

        public Success(@NonNull List<LeaveType> leaveTypes) {
            this.leaveTypes = leaveTypes;
        }

        @NonNull
        public List<LeaveType> getLeaveTypes() {
            return leaveTypes;
        }
    }
    final class Error implements LeaveTypesState {
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
