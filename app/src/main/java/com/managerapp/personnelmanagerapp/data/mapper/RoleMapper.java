package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.RoleResponse;
import com.managerapp.personnelmanagerapp.domain.model.Role;

public class RoleMapper {
    public static Role toRole(RoleResponse response) {
        return new Role(response.getId(), response.getName(), response.getDescription());
    }
}
