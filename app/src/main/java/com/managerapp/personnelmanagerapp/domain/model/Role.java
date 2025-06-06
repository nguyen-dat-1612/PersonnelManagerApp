package com.managerapp.personnelmanagerapp.domain.model;

public enum Role {
    ADMIN,
    MANAGER,
    STAFF,
    USER;


    public static Role fromString(String value) {
        try {
            return Role.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return Role.USER; // default fallback
        }
    }
}
