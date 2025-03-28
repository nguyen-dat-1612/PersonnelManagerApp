package com.managerapp.personnelmanagerapp.ui.state;

import androidx.annotation.NonNull;
import com.managerapp.personnelmanagerapp.domain.model.RewardAssignment;

import java.util.List;

public sealed interface RewardState {
    final class Loading implements RewardState {
        private static final RewardState.Loading INSTANCE = new RewardState.Loading();

        public Loading() {
        }
        public static RewardState.Loading getInstance() {
            return INSTANCE;
        }

    }
    final class Success implements RewardState {
        @NonNull
        public final List<RewardAssignment> rewardAssignments;

        public Success(@NonNull List<RewardAssignment> rewardAssignments) {
            this.rewardAssignments = rewardAssignments;
        }

        @NonNull
        public List<RewardAssignment> getRewardAssignments() {
            return rewardAssignments;
        }
    }
    final class Error implements RewardState {
        public final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
    final class Empty implements RewardState {}
}
