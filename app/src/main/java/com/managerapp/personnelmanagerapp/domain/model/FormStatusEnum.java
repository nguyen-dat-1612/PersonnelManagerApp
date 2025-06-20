package com.managerapp.personnelmanagerapp.domain.model;

import com.managerapp.personnelmanagerapp.R;

public enum FormStatusEnum {
    PENDING,
    APPROVED,
    REJECTED;

    public int getLocalizedStringRes() {
        switch (this) {
            case APPROVED:
                return R.string.form_status_approved;
            case REJECTED:
                return R.string.form_status_rejected;
            case PENDING:
            default:
                return R.string.form_status_pending;
        }
    }
}