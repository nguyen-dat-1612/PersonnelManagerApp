package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.mapper.DepartmentMapper;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.Department;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;

public interface DepartmentRepository {

    Single<List<Department>> getAllDepartments();

    Single<Department> getDepartmentByUserId();
}
