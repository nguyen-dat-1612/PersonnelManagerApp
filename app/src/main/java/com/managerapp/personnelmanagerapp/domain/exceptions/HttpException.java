package com.managerapp.personnelmanagerapp.domain.exceptions;

public class HttpException extends Exception {
    private final int httpCode;

    public HttpException(String message, int httpCode) {
        super(message);
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return httpCode;
    }
}