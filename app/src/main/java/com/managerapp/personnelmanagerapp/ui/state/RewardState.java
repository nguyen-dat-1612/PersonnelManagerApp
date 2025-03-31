package com.managerapp.personnelmanagerapp.ui.state;

import com.managerapp.personnelmanagerapp.domain.model.RewardAssignment;

import java.util.List;

public class RewardState {
    public static class Loading extends RewardState {}

    public static class ListSuccess extends RewardState {
        private final List<RewardAssignment> data;
        public ListSuccess(List<RewardAssignment> data) { this.data = data; }
        public List<RewardAssignment> getData() { return data; }
    }

    public static class DetailSuccess extends RewardState {
        private final RewardAssignment rewardAssignment;
        public DetailSuccess(RewardAssignment rewardAssignment) { this.rewardAssignment = rewardAssignment; }
        public RewardAssignment getRewardAssignment() { return rewardAssignment; }
    }

    public static class Error extends RewardState {
        private final String message;
        public Error(String message) { this.message = message; }
        public String getMessage() { return message; }
    }
}
