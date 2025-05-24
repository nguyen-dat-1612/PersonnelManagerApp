package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class WorkLogResponse {
    @SerializedName("type")
    private String type;
    public String getType() {
        return type;
    }
}
