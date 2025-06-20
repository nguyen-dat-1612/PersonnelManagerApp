package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class JobGradeResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("coefficient")
    private String coefficient;
    @SerializedName("description")
    private String description;


    public JobGradeResponse(String id, String name, String coefficient, String description) {
        this.id = id;
        this.name = name;
        this.coefficient = coefficient;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getCoefficient() {
        return coefficient;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
