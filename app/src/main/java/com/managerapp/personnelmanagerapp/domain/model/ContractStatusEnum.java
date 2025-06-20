package com.managerapp.personnelmanagerapp.domain.model;

import androidx.annotation.StringRes;

import com.managerapp.personnelmanagerapp.R;

public enum ContractStatusEnum {
    PENDING(R.string.contract_status_pending, "🔵"),
    EXPIRED(R.string.contract_status_expired, "⚫"),
    TERMINATED(R.string.contract_status_terminated, "🔴"),
    RENEWED(R.string.contract_status_renewed, "🟢"),
    SIGNED_PENDING_EFFECTIVE(R.string.contract_status_signed, "🟡"),
    ACTIVE(R.string.contract_status_active, "🟢");

    @StringRes
    private final int stringRes;
    private final String icon;

    ContractStatusEnum(@StringRes int stringRes, String icon) {
        this.stringRes = stringRes;
        this.icon = icon;
    }

    public int getStringRes() {
        return stringRes;
    }

    public String getIcon() {
        return icon;
    }

    public String getIconWithText(android.content.Context context) {
        return icon + " " + context.getString(stringRes);
    }

    // Cách sử dụng: contract.getContractStatusEnum().getIconWithText(context);
}
