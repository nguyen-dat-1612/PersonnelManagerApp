package com.managerapp.personnelmanagerapp.domain.model;

import com.google.gson.annotations.SerializedName;

public class Role {
    private String id;

    private String name;

    private String description;

    public Role(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }

}
