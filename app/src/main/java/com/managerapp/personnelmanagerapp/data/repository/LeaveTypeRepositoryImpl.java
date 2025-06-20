package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.mapper.LeaveTypeMapper;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveTypeApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;
import com.managerapp.personnelmanagerapp.domain.repository.LeaveTypeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class LeaveTypeRepositoryImpl implements LeaveTypeRepository {
    private final LeaveTypeApiService apiService;
    private final RxResultHandler rxResultHandler;
    @Inject
    public LeaveTypeRepositoryImpl(LeaveTypeApiService apiService, RxResultHandler rxResultHandler) {
        this.apiService = apiService;
        this.rxResultHandler = rxResultHandler;
    }
    public Single<List<LeaveType>> getAllLeaveTypes() {
        return rxResultHandler.handleSingle(apiService.getAllLeaveTypes())
                .map(LeaveTypeMapper::toLeaveType);

    }
}
