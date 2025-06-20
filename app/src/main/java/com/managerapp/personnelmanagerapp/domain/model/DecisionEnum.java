package com.managerapp.personnelmanagerapp.domain.model;
import android.content.Context;

import androidx.annotation.StringRes;

import com.managerapp.personnelmanagerapp.R;

public enum DecisionEnum {
    AWARD(R.string.decision_award),
    DISCIPLINE(R.string.decision_discipline),
    PROMOTION(R.string.decision_promotion),
    INCREASE_SALARY(R.string.decision_increase_salary),
    SENIORITY_ALLOWANCE(R.string.decision_seniority_allowance),
    TERMINATION(R.string.decision_termination);

    @StringRes
    private final int stringRes;

    DecisionEnum(@StringRes int stringRes) {
        this.stringRes = stringRes;
    }

    public int getStringRes() {
        return stringRes;
    }

    public static DecisionEnum from(String type) {
        return DecisionEnum.valueOf(type.toUpperCase());
    }
    public static String getDecisionString(Context context, DecisionEnum decision) {
        return context.getString(decision.getStringRes());
    }
}
