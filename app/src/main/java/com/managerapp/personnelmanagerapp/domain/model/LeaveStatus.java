package com.managerapp.personnelmanagerapp.domain.model;


public enum LeaveStatus {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private final String status;

    LeaveStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static LeaveStatus fromString(String text) {
        for (LeaveStatus status : LeaveStatus.values()) {
            if (status.status.equalsIgnoreCase(text)) {
                return status;
            }
        }
        return null;
    }
}
