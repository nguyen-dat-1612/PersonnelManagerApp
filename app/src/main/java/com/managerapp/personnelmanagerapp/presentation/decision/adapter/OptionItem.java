package com.managerapp.personnelmanagerapp.presentation.decision.adapter;

import androidx.annotation.NonNull;

public class OptionItem {
    private final Object id;
    private final String label;

    public OptionItem(Object id, String label) {
        this.id = id;
        this.label = label;
    }

    public Object getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    @NonNull
    @Override
    public String toString() {
        return label;
    }
}

