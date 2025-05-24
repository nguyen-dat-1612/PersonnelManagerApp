package com.managerapp.personnelmanagerapp.domain.model;

public enum DecisionEnum {
    AWARD,
    DISCIPLINE,
    PROMOTION,
    INCREASE_SALARY,
    SENIORITY_ALLOWANCE,
    TERMINATION,
    UNKNOWN;

    public static DecisionEnum from(String type) {
        if (type == null) return UNKNOWN;
        try {
            return DecisionEnum.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
