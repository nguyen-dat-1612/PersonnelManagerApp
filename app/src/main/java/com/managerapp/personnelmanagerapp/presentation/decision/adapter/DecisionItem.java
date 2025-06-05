package com.managerapp.personnelmanagerapp.presentation.decision.adapter;

import com.managerapp.personnelmanagerapp.domain.model.DecisionEnum;

public class DecisionItem {
    private final DecisionEnum decisionEnum;
    public final Integer labelResId;

    public DecisionItem(DecisionEnum decisionEnum, Integer labelResId) {
        this.decisionEnum = decisionEnum;
        this.labelResId = labelResId;
    }

    public DecisionEnum getDecisionEnum() {
        return decisionEnum;
    }

    public Integer getLabelResId() {
        return labelResId;
    }
}
