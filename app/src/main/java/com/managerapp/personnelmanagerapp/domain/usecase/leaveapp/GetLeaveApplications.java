package com.managerapp.personnelmanagerapp.domain.usecase.leaveapp;

import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.domain.repository.LeaveApplicationRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetLeaveApplications {
    private final LeaveApplicationRepository repository;
    @Inject
    public GetLeaveApplications(LeaveApplicationRepository repository) {
        this.repository = repository;
    }

    public Single<List<LeaveApplication>> execute() {
        return repository.getLeaveApplications();
    }
}
