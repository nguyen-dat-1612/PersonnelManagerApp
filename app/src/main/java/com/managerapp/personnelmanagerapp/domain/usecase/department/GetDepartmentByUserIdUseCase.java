package com.managerapp.personnelmanagerapp.domain.usecase.department;

import com.managerapp.personnelmanagerapp.data.repository.DepartmentRepository;
import com.managerapp.personnelmanagerapp.domain.model.Department;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetDepartmentByUserIdUseCase {
    private final DepartmentRepository departmentRepository;
    @Inject
    public GetDepartmentByUserIdUseCase(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Single<Department> execute() {
        return departmentRepository.getDepartmentByUserId();
    }
}
