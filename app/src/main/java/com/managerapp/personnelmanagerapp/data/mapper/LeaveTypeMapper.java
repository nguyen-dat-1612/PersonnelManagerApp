package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveTypeResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;

import java.util.List;
import java.util.stream.Collectors;

public class LeaveTypeMapper {
    public static LeaveType toLeaveType(LeaveTypeResponse response) {
        return new LeaveType(
                response.getId(),
                response.getName()
        );
    }

    public static List<LeaveType> toLeaveType(List<LeaveTypeResponse> responses) {
        return responses.stream()
                .map(LeaveTypeMapper::toLeaveType)
                .collect(Collectors.toList());
    }
}
