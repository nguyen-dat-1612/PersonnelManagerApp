package com.managerapp.personnelmanagerapp.presentation.worklog.ui;

import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class WorkLogUiState {
    private final boolean isLoading;
    private final List<WorkLogResponse> workLogs;
    private final String errorMessage;

    public WorkLogUiState(boolean isLoading,
                           List<WorkLogResponse> workLogs,
                           String errorMessage) {
        this.isLoading = isLoading;
        this.workLogs = workLogs != null ? workLogs : null;
        this.errorMessage = errorMessage;
    }

    // Factory methods for clear states
    public static WorkLogUiState loading() {
        return new WorkLogUiState(true, null, null);
    }

    public static WorkLogUiState success(List<WorkLogResponse> workLogs) {
        return new WorkLogUiState(false, workLogs, null);
    }

    public static WorkLogUiState error(String errorMessage) {
        return new WorkLogUiState(false, null, errorMessage);
    }

    // Getters only - no setters
    public boolean isLoading() {
        return isLoading;
    }

    public List<WorkLogResponse> getWorkLogs() {
        return workLogs;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // Proper equals/hashCode for state comparisons
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkLogUiState that = (WorkLogUiState) o;
        return isLoading == that.isLoading &&
                Objects.equals(workLogs, that.workLogs) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isLoading, workLogs, errorMessage);
    }

    @Override
    public String toString() {
        return "WorkLogUiState{" +
                "isLoading=" + isLoading +
                ", workLogs=" + workLogs +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}