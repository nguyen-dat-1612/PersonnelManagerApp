package com.managerapp.personnelmanagerapp.presentation.decision.state;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.model.Decision;

import java.util.List;

public class DecisionListUiState {
    private final boolean isLoading;
    private final List<Decision> decisions;
    private final String errorMessage;
    private final DecisionState stats;

    public DecisionListUiState(boolean isLoading, List<Decision> decisions, String errorMessage, DecisionState stats) {
        this.isLoading = isLoading;
        this.decisions = decisions;
        this.errorMessage = errorMessage;
        this.stats = stats;
    }

    public static DecisionListUiState loading() {
        return new DecisionListUiState(true, null, null, null);
    }

    public static DecisionListUiState success(List<Decision> decisions) {
        return new DecisionListUiState(false, decisions, null, DecisionState.from(decisions));
    }

    public static DecisionListUiState error(String errorMessage) {
        return new DecisionListUiState(false, null, errorMessage, null);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public List<Decision> getDecisions() {
        return decisions;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public DecisionState getStats() {
        return stats;
    }
}
