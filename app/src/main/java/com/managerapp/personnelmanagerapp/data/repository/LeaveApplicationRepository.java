package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.LeaveApplicationApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.utils.manager.LocalDataManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class LeaveApplicationRepository {
    private final LeaveApplicationApiService apiService;
    private final LocalDataManager localDataManager;
    @Inject
    public LeaveApplicationRepository(LeaveApplicationApiService apiService, LocalDataManager localDataManager) {
        this.apiService = apiService;
        this.localDataManager = localDataManager;
    }

    public Single<List<LeaveApplicationResponse>> getLeaveApplications() {
        return localDataManager.getUserIdAsync()
                .flatMap( userId -> {
                    return RxResultHandler.handle(apiService.getLeaveApplications(userId));
                });
    }

    public Single<LeaveApplication> getLeaveApplicationById(String id) {
        return RxResultHandler.handle(apiService.getLeaveApplicationById(id));
    }

    public Single<LeaveApplicationResponse> createLeaveApplication(LeaveApplicationRequest request) {
        return RxResultHandler.handle(apiService.createLeaveApplication(request));
    }

    public Single<List<LeaveApplicationResponse>> getApplicationIsPending(String formStatusEnum, String departmentId) {
        return RxResultHandler.handle(apiService.getApplicationIsPending(formStatusEnum,departmentId));
    }

    public Single<LeaveApplicationResponse> confirmApplication(long id, String status) {
        return RxResultHandler.handle(apiService.confirmApplication(id, status));
    }
}
