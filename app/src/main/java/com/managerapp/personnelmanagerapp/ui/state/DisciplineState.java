package com.managerapp.personnelmanagerapp.ui.state;

import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.domain.model.DisciplineAssignment;

import java.util.List;

public class DisciplineState {
    public static class Loading extends DisciplineState {}

    public static class ListSuccess extends DisciplineState {
        private final List<AssignmentResponse> data;
        public ListSuccess(List<AssignmentResponse> data) { this.data = data; }
        public List<AssignmentResponse> getData() { return data; }
    }
    public static class Error extends DisciplineState {
        private final String message;
        public Error(String message) { this.message = message; }
        public String getMessage() { return message; }
    }
}

