package com.managerapp.personnelmanagerapp.domain.model;

public class DataItem {
    private final String key;
    private final String value;

    public DataItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public String getValue() { return value; }
}