package com.managerapp.personnelmanagerapp.domain.usecase.leaveapp;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.data.repository.LeaveApplicationRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.LeaveApplicationRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class ConfirmApplicationUseCase {
    private final LeaveApplicationRepository leaveApplicationRepository;

    @Inject
    public ConfirmApplicationUseCase(LeaveApplicationRepository leaveApplicationRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    public Single<LeaveApplicationResponse> execute(long applicationId, String formStatusEnum) {
        return leaveApplicationRepository.confirmApplication(applicationId, formStatusEnum);
    }
}
