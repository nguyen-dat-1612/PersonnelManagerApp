package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse2 {
    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private boolean data;

    public int getCode() {
        return code;
    }

    public boolean getData() {
        return data;
    }

}
