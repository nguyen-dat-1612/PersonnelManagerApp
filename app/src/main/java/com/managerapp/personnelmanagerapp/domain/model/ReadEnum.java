package com.managerapp.personnelmanagerapp.domain.model;

import android.content.Context;

import androidx.annotation.StringRes;

import com.managerapp.personnelmanagerapp.R;

public enum ReadEnum {
    ALL(R.string.filter_all),
    READ(R.string.filter_read),
    UNREAD(R.string.filter_unread);

    @StringRes
    private final int labelResId;

    ReadEnum(@StringRes int labelResId) {
        this.labelResId = labelResId;
    }

    public int getLabelResId() {
        return labelResId;
    }

    public String getLabel(Context context) {
        return context.getString(labelResId);
    }
}

