package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;
import com.managerapp.personnelmanagerapp.domain.model.Role;

import java.io.Serializable;

public class PositionResponse implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("department")
    private DepartmentResponse department;

    @SerializedName("role")
    private RoleResponse roleResponse;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public DepartmentResponse getDepartment() {
        return department;
    }

    public Role getRoleEnum() {
        if ((roleResponse.getName() == null)) {
            return null;
        }
        return Role.valueOf(roleResponse.getName());
    }

    public RoleResponse getRole() {
        return roleResponse;
    }

}

