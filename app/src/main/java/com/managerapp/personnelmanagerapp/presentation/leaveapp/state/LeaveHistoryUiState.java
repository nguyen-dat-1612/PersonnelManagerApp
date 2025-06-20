package com.managerapp.personnelmanagerapp.presentation.leaveapp.state;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.domain.model.LeaveStatus;

import java.util.List;

public class LeaveHistoryUiState {
    private final boolean isLoading;
    private final List<LeaveApplication> leaveApplications;
    private final String errorMessage;
    private final int approvedCount;
    private final int pendingCount;
    private final int rejectedCount;

    public LeaveHistoryUiState(boolean isLoading,
                               List<LeaveApplication> leaveApplications,
                               String errorMessage,
                               int approvedCount,
                               int pendingCount,
                               int rejectedCount) {
        this.isLoading = isLoading;
        this.leaveApplications = leaveApplications;
        this.errorMessage = errorMessage;
        this.approvedCount = approvedCount;
        this.pendingCount = pendingCount;
        this.rejectedCount = rejectedCount;
    }

    // Getters
    public boolean isLoading() {
        return isLoading;
    }

    public List<LeaveApplication> getLeaveApplications() {
        return leaveApplications;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getApprovedCount() {
        return approvedCount;
    }

    public int getPendingCount() {
        return pendingCount;
    }

    public int getRejectedCount() {
        return rejectedCount;
    }

    // Factory methods
    public static LeaveHistoryUiState loading() {
        return new LeaveHistoryUiState(true, null, null, 0, 0, 0);
    }

    public static LeaveHistoryUiState success(List<LeaveApplication> leaveApplications) {
        LeaveStats stats = countLeaveStatus(leaveApplications);
        return new LeaveHistoryUiState(false, leaveApplications, null,
                stats.approvedCount, stats.pendingCount, stats.rejectedCount);
    }

    public static LeaveHistoryUiState error(String errorMessage) {
        return new LeaveHistoryUiState(false, null, errorMessage, 0, 0, 0);
    }

    private static LeaveStats countLeaveStatus(List<LeaveApplication> applications) {
        int approvedCount = 0, pendingCount = 0, rejectedCount = 0;

        if (applications != null) {
            for (LeaveApplication application : applications) {
                FormStatusEnum status = application.getFormStatusEnum();
                if (status != null) {
                    switch (status) {
                        case APPROVED:
                            approvedCount++;
                            break;
                        case PENDING:
                            pendingCount++;
                            break;
                        case REJECTED:
                            rejectedCount++;
                            break;
                    }
                }
            }
        }
        return new LeaveStats(approvedCount, pendingCount, rejectedCount);
    }

    private static class LeaveStats {
        final int approvedCount;
        final int pendingCount;
        final int rejectedCount;

        LeaveStats(int approvedCount, int pendingCount, int rejectedCount) {
            this.approvedCount = approvedCount;
            this.pendingCount = pendingCount;
            this.rejectedCount = rejectedCount;
        }
    }
}