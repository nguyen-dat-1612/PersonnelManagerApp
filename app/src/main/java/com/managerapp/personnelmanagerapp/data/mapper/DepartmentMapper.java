package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.DepartmentResponse;
import com.managerapp.personnelmanagerapp.domain.model.Department;

public class DepartmentMapper {
    public static Department toDepartment(DepartmentResponse response) {
        return new Department(response.id, response.name, response.description);
    }
}
