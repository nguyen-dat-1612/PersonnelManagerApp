package com.managerapp.personnelmanagerapp.ui.state;

import com.managerapp.personnelmanagerapp.domain.model.DisciplineAssignment;

import java.util.List;

public class DisciplineState {
    public static class Loading extends DisciplineState {}

    public static class ListSuccess extends DisciplineState {
        private final List<DisciplineAssignment> data;
        public ListSuccess(List<DisciplineAssignment> data) { this.data = data; }
        public List<DisciplineAssignment> getData() { return data; }
    }

    public static class DetailSuccess extends DisciplineState {
        private final DisciplineAssignment disciplineAssignment;
        public DetailSuccess(DisciplineAssignment disciplineAssignment) { this.disciplineAssignment = disciplineAssignment; }
        public DisciplineAssignment getSelectedDiscipline() { return disciplineAssignment; }
    }

    public static class Error extends DisciplineState {
        private final String message;
        public Error(String message) { this.message = message; }
        public String getMessage() { return message; }
    }
}

