package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LeaveApplicationMapper {
    public static LeaveApplication toLeaveApplication(LeaveApplicationResponse leaveApplication) {
        return new LeaveApplication.Builder()
                .id(leaveApplication.getId())
                .startDate(leaveApplication.getStartDate())
                .endDate(leaveApplication.getEndDate())
                .reason(leaveApplication.getReason())
                .formStatusEnum(leaveApplication.getFormStatusEnum())
                .user(UserSummaryMapper.toUserSummary(leaveApplication.getUser()))
                .signer(Optional.ofNullable(leaveApplication.getSigner())
                        .map(UserSummaryMapper::toUserSummary)
                        .orElse(null))
                .leaveTypeName(leaveApplication.getLeaveTypeName())
                .build();
    }

    public static List<LeaveApplication> toLeaveApplication(List<LeaveApplicationResponse> leaveApplications) {
        List<LeaveApplication> result = new ArrayList<>();
        for (LeaveApplicationResponse leaveApplication : leaveApplications) {
            result.add(toLeaveApplication(leaveApplication));
        }
        return result;
    }
}
