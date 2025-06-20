package com.managerapp.personnelmanagerapp.domain.usecase.leaveapp;

import com.managerapp.personnelmanagerapp.domain.model.LeaveType;
import com.managerapp.personnelmanagerapp.domain.repository.LeaveTypeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetAllLeaveTypeUseCase {
    private final LeaveTypeRepository leaveTypeRepository;

    @Inject
    public GetAllLeaveTypeUseCase(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    public Single<List<LeaveType>> execute() {
        return leaveTypeRepository.getAllLeaveTypes();
    }
}
