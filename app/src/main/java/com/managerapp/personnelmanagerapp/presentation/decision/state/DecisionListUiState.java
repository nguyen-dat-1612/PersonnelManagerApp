package com.managerapp.personnelmanagerapp.presentation.decision.state;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;

import java.util.List;

public class DecisionListUiState {
    private final boolean isLoading;
    private final List<DecisionResponse> decisions;
    private final String errorMessage;
    private final DecisionStats stats;

    public DecisionListUiState(boolean isLoading, List<DecisionResponse> decisions, String errorMessage, DecisionStats stats) {
        this.isLoading = isLoading;
        this.decisions = decisions;
        this.errorMessage = errorMessage;
        this.stats = stats;
    }

    public static DecisionListUiState loading() {
        return new DecisionListUiState(true, null, null, null);
    }

    public static DecisionListUiState success(List<DecisionResponse> decisions) {
        return new DecisionListUiState(false, decisions, null, DecisionStats.from(decisions));
    }

    public static DecisionListUiState error(String errorMessage) {
        return new DecisionListUiState(false, null, errorMessage, null);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public List<DecisionResponse> getDecisions() {
        return decisions;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public DecisionStats getStats() {
        return stats;
    }
}
