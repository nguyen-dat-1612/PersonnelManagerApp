package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface LeaveApplicationRepository {
    Single<List<LeaveApplicationResponse>> getLeaveApplications();

    Single<LeaveApplication> getLeaveApplicationById(String id);

    Single<LeaveApplicationResponse> createLeaveApplication(LeaveApplicationRequest request);

    Single<List<LeaveApplicationResponse>> getApplicationIsPending(String formStatusEnum, String departmentId);

    Single<LeaveApplicationResponse> confirmApplication(long id, String status);
}
