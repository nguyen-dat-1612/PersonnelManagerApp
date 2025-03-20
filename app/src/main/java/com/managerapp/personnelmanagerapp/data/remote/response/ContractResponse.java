package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class ContractResponse<T> {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;


    @SerializedName("data")
    private T data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
