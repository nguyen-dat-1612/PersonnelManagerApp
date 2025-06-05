package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.mapper.DepartmentMapper;
import com.managerapp.personnelmanagerapp.data.remote.api.DepartmentApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.Department;
import com.managerapp.personnelmanagerapp.domain.repository.DepartmentRepository;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    private final DepartmentApiService apiService;
    private final LocalDataManager localDataManager;
    @Inject
    public DepartmentRepositoryImpl(DepartmentApiService apiService, LocalDataManager localDataManager) {
        this.apiService = apiService;
        this.localDataManager = localDataManager;
    }


    public Single<List<Department>> getAllDepartments() {
        return RxResultHandler.handle(apiService.getAllDepartments()).
                map(listResponse -> listResponse.stream()
                            .map(DepartmentMapper::toDepartment)
                            .collect(Collectors.toList())
                );

    }

    public Single<Department> getDepartmentByUserId() {
        return localDataManager.getUserIdAsync()
                .flatMap(userId -> RxResultHandler.handle(apiService.getDepartmentByUserId(userId)).map(DepartmentMapper::toDepartment));

    }
}
