package com.managerapp.personnelmanagerapp.presentation.contract.state;

import com.managerapp.personnelmanagerapp.data.remote.response.JobGradeResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.domain.model.JobGrade;

import java.util.List;

public abstract class RequestUpgradeUiState {

    public static final class Loading extends RequestUpgradeUiState {}

    public static final class DataLoaded extends RequestUpgradeUiState {
        public final List<JobGrade> jobGrades;
        public final UserProfileResponse userProfile;
        public final JobGrade jobGradeById;

        public DataLoaded(List<JobGrade> jobGrades,
                          UserProfileResponse userProfile,
                          JobGrade jobGradeById) {
            this.jobGrades = jobGrades;
            this.userProfile = userProfile;
            this.jobGradeById = jobGradeById;
        }

        public List<JobGrade> jobGrades() {
            return jobGrades;
        }

        public UserProfileResponse getUser() {
            return userProfile;
        }

        public JobGrade getCurrentJobGrade() {
            return jobGradeById;
        }
    }

    public static final class Error extends RequestUpgradeUiState {
        public final String message;
        public final List<JobGrade> jobGrades;
        public final UserProfileResponse userProfile;
        public final JobGrade jobGradeById;

        public Error(String message,
                     List<JobGrade> jobGrades,
                     UserProfileResponse userProfile,
                     JobGrade jobGradeById) {
            this.message = message;
            this.jobGrades = jobGrades;
            this.userProfile = userProfile;
            this.jobGradeById = jobGradeById;
        }
    }
}

