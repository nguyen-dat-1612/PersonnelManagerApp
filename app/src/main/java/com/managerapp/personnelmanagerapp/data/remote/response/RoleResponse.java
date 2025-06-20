package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class RoleResponse implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

}