package com.managerapp.personnelmanagerapp.domain.model;

public enum RoleEnum {
    ADMIN,
    MANAGER,
    STAFF,
    USER;

    public static RoleEnum fromString(String value) {
        try {
            return RoleEnum.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return RoleEnum.USER; // default fallback
        }
    }
}
