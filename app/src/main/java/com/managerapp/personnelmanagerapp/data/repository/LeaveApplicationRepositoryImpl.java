package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.LeaveApplicationApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.domain.repository.LeaveApplicationRepository;
import com.managerapp.personnelmanagerapp.utils.manager.LocalDataManager;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Singleton
public class LeaveApplicationRepositoryImpl implements LeaveApplicationRepository {
    private final LeaveApplicationApiService apiService;
    private final LocalDataManager localDataManager;
    @Inject
    public LeaveApplicationRepositoryImpl(LeaveApplicationApiService apiService, LocalDataManager localDataManager) {
        this.apiService = apiService;
        this.localDataManager = localDataManager;
    }

    public Single<List<LeaveApplicationResponse>> getLeaveApplications() {
        return localDataManager.getUserIdAsync()
                .flatMap( userId -> RxResultHandler.handle(apiService.getLeaveApplications(userId)));
    }

    public Single<LeaveApplication> getLeaveApplicationById(String id) {
        return RxResultHandler.handle(apiService.getLeaveApplicationById(id));
    }

    public Single<LeaveApplicationResponse> createLeaveApplication(LeaveApplicationRequest request) {
        return RxResultHandler.handle(apiService.createLeaveApplication(request));
    }

    @Override
    public Single<List<LeaveApplicationResponse>> getApplicationIsPending(String formStatusEnum, String departmentId) {
        return RxResultHandler.handle(apiService.getApplicationIsPending(formStatusEnum, departmentId))
                .flatMap(response -> localDataManager.getUserIdAsync()
                        .map(userId -> response.stream()
                                .filter(app -> app.getUser() != null && !userId.equals(app.getUser().getId()))
                                .collect(Collectors.toList())
                        ));
    }


    public Single<LeaveApplicationResponse> confirmApplication(long id, String status) {
        return RxResultHandler.handle(apiService.confirmApplication(id, status));
    }
}
