package com.managerapp.personnelmanagerapp.data.repository;

import com.google.gson.Gson;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveTypeApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

public class LeaveTypeRepository {
    private final LeaveTypeApiService apiService;
    @Inject
    public LeaveTypeRepository(LeaveTypeApiService apiService) {
        this.apiService = apiService;
    }
    public Single<List<LeaveType>> getAllLeaveTypes() {
        return RxResultHandler.handle(apiService.getAllLeaveTypes());

    }
}
