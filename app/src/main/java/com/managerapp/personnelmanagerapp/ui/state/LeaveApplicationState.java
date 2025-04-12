package com.managerapp.personnelmanagerapp.ui.state;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;

import java.util.List;

public sealed interface LeaveApplicationState {
    final class Loading implements LeaveApplicationState {}
    final class Success implements LeaveApplicationState {
        public final List<LeaveApplicationResponse> leaveApplications;

        public Success(List<LeaveApplicationResponse> leaveApplications) {
            this.leaveApplications = leaveApplications;
        }

        public List<LeaveApplicationResponse> getLeaveApplications() {
            return leaveApplications;
        }
    }
    final class Error implements LeaveApplicationState{
        public final String message;

        public Error(String message) {
            this.message = message;
        }
    }
    final class Empty implements LeaveApplicationState{}

}
