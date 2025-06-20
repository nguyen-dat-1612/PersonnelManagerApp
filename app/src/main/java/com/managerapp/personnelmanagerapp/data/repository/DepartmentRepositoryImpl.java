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
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class DepartmentRepositoryImpl implements DepartmentRepository {
    private final DepartmentApiService apiService;
    private final LocalDataManager localDataManager;
    private final RxResultHandler rxResultHandler;
    @Inject
    public DepartmentRepositoryImpl(DepartmentApiService apiService, LocalDataManager localDataManager, com.managerapp.personnelmanagerapp.data.utils.RxResultHandler rxResultHandler) {
        this.apiService = apiService;
        this.localDataManager = localDataManager;
        this.rxResultHandler = rxResultHandler;
    }
    public Single<List<Department>> getAllDepartments() {
        return rxResultHandler.handleSingle(apiService.getAllDepartments()).
                map(listResponse -> listResponse.stream()
                            .map(DepartmentMapper::toDepartment)
                            .collect(Collectors.toList())
                );

    }
    public Single<Department> getDepartmentByUserId() {
        return localDataManager.getUserIdAsync()
                .flatMap(userId -> rxResultHandler.handleSingle(apiService.getDepartmentByUserId(userId)).map(DepartmentMapper::toDepartment));

    }
}
