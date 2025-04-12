package com.managerapp.personnelmanagerapp.ui.state;

import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.domain.model.RewardAssignment;

import java.util.List;

public class RewardState {
    public static class Loading extends RewardState {}

    public static class ListSuccess extends RewardState {
        private final List<AssignmentResponse> data;
        public ListSuccess(List<AssignmentResponse> data) { this.data = data; }
        public List<AssignmentResponse> getData() { return data; }
    }

    public static class Error extends RewardState {
        private final String message;
        public Error(String message) { this.message = message; }
        public String getMessage() { return message; }
    }
}
