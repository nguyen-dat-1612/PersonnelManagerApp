package com.managerapp.personnelmanagerapp.domain.usecase.leaveapp;

import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.domain.repository.LeaveApplicationRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class CreateLeaveAppUseCase {
    private final LeaveApplicationRepository leaveApplicationRepository;

    @Inject
    public CreateLeaveAppUseCase(LeaveApplicationRepository leaveApplicationRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    public Single<LeaveApplication> execute(LeaveApplicationRequest leaveApplicationRequest) {
        return leaveApplicationRepository.createLeaveApplication(leaveApplicationRequest);
    }
}
