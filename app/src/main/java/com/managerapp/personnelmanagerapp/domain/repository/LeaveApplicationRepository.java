package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface LeaveApplicationRepository {
    Single<List<LeaveApplication>> getLeaveApplications();
    Single<LeaveApplication> getLeaveApplicationById(String id);
    Single<LeaveApplication> createLeaveApplication(LeaveApplicationRequest request);
    Single<List<LeaveApplication>> getApplicationIsPending(FormStatusEnum formStatusEnum, String departmentId);
    Single<LeaveApplication> confirmApplication(long id, FormStatusEnum status);

}
