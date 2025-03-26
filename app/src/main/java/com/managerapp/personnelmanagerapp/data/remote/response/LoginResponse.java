package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private String token;

    public int getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }
}
