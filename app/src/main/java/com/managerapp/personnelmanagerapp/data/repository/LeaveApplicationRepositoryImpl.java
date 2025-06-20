package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.mapper.LeaveApplicationMapper;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveApplicationApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.domain.repository.LeaveApplicationRepository;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class LeaveApplicationRepositoryImpl implements LeaveApplicationRepository {
    private final LeaveApplicationApiService apiService;
    private final LocalDataManager localDataManager;

    private final RxResultHandler rxResultHandler;
    @Inject
    public LeaveApplicationRepositoryImpl(LeaveApplicationApiService apiService, LocalDataManager localDataManager, RxResultHandler rxResultHandler) {
        this.apiService = apiService;
        this.localDataManager = localDataManager;
        this.rxResultHandler = rxResultHandler;
    }

    public Single<List<LeaveApplication>> getLeaveApplications() {
        return localDataManager.getUserIdAsync()
                .flatMap( userId -> rxResultHandler.handleSingle(apiService.getLeaveApplications(userId)))
                .map(LeaveApplicationMapper::toLeaveApplication);
    }

    public Single<LeaveApplication> getLeaveApplicationById(String id) {
        return rxResultHandler.handleSingle(apiService.getLeaveApplicationById(id))
                .map(LeaveApplicationMapper::toLeaveApplication);
    }

    public Single<LeaveApplication> createLeaveApplication(LeaveApplicationRequest request) {
        return rxResultHandler.handleSingle(apiService.createLeaveApplication(request))
                .map(LeaveApplicationMapper::toLeaveApplication);
    }

    @Override
    public Single<List<LeaveApplication>> getApplicationIsPending(FormStatusEnum formStatusEnum, String departmentId) {
        return rxResultHandler.handleSingle(apiService.getApplicationIsPending(formStatusEnum, departmentId))
                .flatMap(response -> localDataManager.getUserIdAsync()
                        .map(userId -> response.stream()
                                .filter(app -> app.getUser() != null && !userId.equals(app.getUser().getId()))
                                .map(LeaveApplicationMapper::toLeaveApplication)
                                .collect(Collectors.toList())
                        ));
    }

    public Single<LeaveApplication> confirmApplication(long id, FormStatusEnum status) {
        return rxResultHandler.handleSingle(apiService.confirmApplication(id, status))
                .map(LeaveApplicationMapper::toLeaveApplication);
    }
}
