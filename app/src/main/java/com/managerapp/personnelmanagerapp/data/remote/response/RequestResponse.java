package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class RequestResponse {
    @SerializedName("data")
    private String status;
    public String getStatus() {
        return status;
    }
}
