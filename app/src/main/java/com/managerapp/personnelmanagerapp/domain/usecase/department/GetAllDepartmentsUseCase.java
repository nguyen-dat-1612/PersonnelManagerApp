package com.managerapp.personnelmanagerapp.domain.usecase.department;

import com.managerapp.personnelmanagerapp.domain.model.Department;
import com.managerapp.personnelmanagerapp.domain.repository.DepartmentRepository;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Single;

public class GetAllDepartmentsUseCase {
    private final DepartmentRepository departmentRepository;
    @Inject
    public GetAllDepartmentsUseCase(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Single<List<Department>> execute() {
        return departmentRepository.getAllDepartments();
    }
}
