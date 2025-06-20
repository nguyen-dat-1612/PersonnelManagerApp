package com.managerapp.personnelmanagerapp.presentation.leaveapp.state;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;

import java.util.List;

public interface CreateLeaveUiState {

    class Loading implements CreateLeaveUiState {}

    class DataLoaded implements CreateLeaveUiState {
        public final List<LeaveType> leaveTypes;
        public final UserProfileResponse userProfile;

        public DataLoaded(List<LeaveType> leaveTypes, UserProfileResponse userProfile) {
            this.leaveTypes = leaveTypes;
            this.userProfile = userProfile;
        }
    }

    class LeaveCreated implements CreateLeaveUiState {
        public final LeaveApplication response;

        public LeaveCreated(LeaveApplication response) {
            this.response = response;
        }
    }

    class Error implements CreateLeaveUiState {
        public final String message;
        public final List<LeaveType> cachedLeaveTypes;
        public final UserProfileResponse cachedUserProfile;

        public Error(String message, List<LeaveType> cachedLeaveTypes, UserProfileResponse cachedUserProfile) {
            this.message = message;
            this.cachedLeaveTypes = cachedLeaveTypes;
            this.cachedUserProfile = cachedUserProfile;
        }
    }
}
