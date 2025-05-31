package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.LeaveTypeApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;
import com.managerapp.personnelmanagerapp.domain.repository.LeaveTypeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class LeaveTypeRepositoryImpl implements LeaveTypeRepository {
    private final LeaveTypeApiService apiService;
    @Inject
    public LeaveTypeRepositoryImpl(LeaveTypeApiService apiService) {
        this.apiService = apiService;
    }
    public Single<List<LeaveType>> getAllLeaveTypes() {
        return RxResultHandler.handle(apiService.getAllLeaveTypes());

    }
}
