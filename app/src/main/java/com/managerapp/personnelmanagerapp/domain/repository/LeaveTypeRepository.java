package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface LeaveTypeRepository {
    Single<List<LeaveType>> getAllLeaveTypes();
}
