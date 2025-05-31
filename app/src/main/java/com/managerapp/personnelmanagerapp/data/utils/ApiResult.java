package com.managerapp.personnelmanagerapp.data.utils;

public class ApiResult<T> {
    public final T data;
    public final Throwable error;

    public ApiResult(T data, Throwable error) {
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(data, null);
    }

    public static <T> ApiResult<T> error(Throwable error) {
        return new ApiResult<>(null, error);
    }

    public boolean isSuccess() {
        return data != null;
    }
}
