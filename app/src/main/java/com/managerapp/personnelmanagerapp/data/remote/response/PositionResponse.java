package com.managerapp.personnelmanagerapp.data.remote.response;

import com.google.gson.annotations.SerializedName;
import com.managerapp.personnelmanagerapp.domain.model.RoleEnum;

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

    public RoleEnum getRoleEnum() {
        if ((roleResponse.getName() == null)) {
            return null;
        }
        return RoleEnum.valueOf(roleResponse.getName());
    }

    public RoleResponse getRole() {
        return roleResponse;
    }

}

