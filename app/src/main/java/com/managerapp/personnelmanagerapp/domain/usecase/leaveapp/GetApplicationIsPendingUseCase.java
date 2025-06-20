package com.managerapp.personnelmanagerapp.domain.usecase.leaveapp;

import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.domain.repository.LeaveApplicationRepository;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Single;

public class GetApplicationIsPendingUseCase {
    private final LeaveApplicationRepository leaveApplicationRepository;
    @Inject
    public GetApplicationIsPendingUseCase(LeaveApplicationRepository leaveApplicationRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
    }
    public Single<List<LeaveApplication>> execute(FormStatusEnum formStatusEnum, String departmentId) {
        return leaveApplicationRepository.getApplicationIsPending(formStatusEnum, departmentId);
    }
}
