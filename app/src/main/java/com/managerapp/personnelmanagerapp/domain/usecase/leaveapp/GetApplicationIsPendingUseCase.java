package com.managerapp.personnelmanagerapp.domain.usecase.leaveapp;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.data.repository.LeaveApplicationRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetApplicationIsPendingUseCase {
    private final LeaveApplicationRepository leaveApplicationRepository;

    @Inject
    public GetApplicationIsPendingUseCase(LeaveApplicationRepository leaveApplicationRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
    }
    public Single<List<LeaveApplicationResponse>> execute(String formStatusEnum, String departmentId) {
        return leaveApplicationRepository.getApplicationIsPending(formStatusEnum, departmentId);
    }
}
