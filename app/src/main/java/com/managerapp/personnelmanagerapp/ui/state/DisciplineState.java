package com.managerapp.personnelmanagerapp.ui.state;

import androidx.annotation.NonNull;

import com.managerapp.personnelmanagerapp.domain.model.DisciplineAssignment;

import java.util.List;

public sealed interface DisciplineState {
    final class Loading implements DisciplineState {
        private static final DisciplineState.Loading INSTANCE = new DisciplineState.Loading();

        public Loading() {
        }
        public static DisciplineState.Loading getInstance() {
            return INSTANCE;
        }

    }
    final class Success implements DisciplineState {
        @NonNull
        public final List<DisciplineAssignment> disciplineAssignments;

        public Success(@NonNull List<DisciplineAssignment> notifications) {
            this.disciplineAssignments = notifications;
        }

        @NonNull
        public List<DisciplineAssignment> getDisciplineAssignments() {
            return disciplineAssignments;
        }
    }
    final class Error implements DisciplineState {
        public final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
    final class Empty implements DisciplineState {}
}
