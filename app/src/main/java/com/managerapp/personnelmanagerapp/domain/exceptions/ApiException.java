package com.managerapp.personnelmanagerapp.domain.exceptions;

public class ApiException extends Exception {
    private final int code;

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}