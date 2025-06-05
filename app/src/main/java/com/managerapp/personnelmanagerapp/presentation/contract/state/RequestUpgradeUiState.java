package com.managerapp.personnelmanagerapp.presentation.contract.state;

import com.managerapp.personnelmanagerapp.data.remote.response.JobGradeResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;

import java.util.List;

public abstract class RequestUpgradeUiState {

    public static final class Loading extends RequestUpgradeUiState {}

    public static final class DataLoaded extends RequestUpgradeUiState {
        public final List<JobGradeResponse> jobGrades;
        public final UserProfileResponse userProfile;
        public final JobGradeResponse jobGradeById;

        public DataLoaded(List<JobGradeResponse> jobGrades,
                          UserProfileResponse userProfile,
                          JobGradeResponse jobGradeById) {
            this.jobGrades = jobGrades;
            this.userProfile = userProfile;
            this.jobGradeById = jobGradeById;
        }

        public List<JobGradeResponse> jobGrades() {
            return jobGrades;
        }

        public UserProfileResponse getUser() {
            return userProfile;
        }

        public JobGradeResponse getCurrentJobGrade() {
            return jobGradeById;
        }
    }

    public static final class Error extends RequestUpgradeUiState {
        public final String message;
        public final List<JobGradeResponse> jobGrades;
        public final UserProfileResponse userProfile;
        public final JobGradeResponse jobGradeById;

        public Error(String message,
                     List<JobGradeResponse> jobGrades,
                     UserProfileResponse userProfile,
                     JobGradeResponse jobGradeById) {
            this.message = message;
            this.jobGrades = jobGrades;
            this.userProfile = userProfile;
            this.jobGradeById = jobGradeById;
        }
    }
}

