package com.managerapp.personnelmanagerapp.domain.usecase.leaveapp;

import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.domain.repository.LeaveApplicationRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class ConfirmApplicationUseCase {
    private final LeaveApplicationRepository leaveApplicationRepository;

    @Inject
    public ConfirmApplicationUseCase(LeaveApplicationRepository leaveApplicationRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
    }

    public Single<LeaveApplication> execute(long applicationId, FormStatusEnum formStatusEnum) {
        return leaveApplicationRepository.confirmApplication(applicationId, formStatusEnum);
    }
}
