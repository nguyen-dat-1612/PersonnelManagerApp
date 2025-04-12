package com.managerapp.personnelmanagerapp.domain.usecase;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.data.repository.LeaveApplicationRepository;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetLeaveApplications {
    private final LeaveApplicationRepository repository;
    @Inject
    public GetLeaveApplications(LeaveApplicationRepository repository) {
        this.repository = repository;
    }

    public Single<List<LeaveApplicationResponse>> excute(int userId) {
        return repository.getLeaveApplications(userId);
    }
}
