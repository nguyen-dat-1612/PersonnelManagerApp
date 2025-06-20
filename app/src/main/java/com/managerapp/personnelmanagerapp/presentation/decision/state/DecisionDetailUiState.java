package com.managerapp.personnelmanagerapp.presentation.decision.state;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.model.Decision;

public class DecisionDetailUiState {
    private final boolean isLoading;
    private final Decision decision;
    private final String errorMessage;

    public DecisionDetailUiState(boolean isLoading, Decision decision, String errorMessage) {
        this.isLoading = isLoading;
        this.decision = decision;
        this.errorMessage = errorMessage;
    }

    public static DecisionDetailUiState loading() {
        return new DecisionDetailUiState(true, null, null);
    }

    public static DecisionDetailUiState success(Decision decision) {
        return new DecisionDetailUiState(false, decision, null);
    }

    public static DecisionDetailUiState error(String errorMessage) {
        return new DecisionDetailUiState(false, null, errorMessage);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public Decision getDecision() {
        return decision;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
