package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.UserSummaryResponse;
import com.managerapp.personnelmanagerapp.domain.model.UserSummary;

import java.util.ArrayList;
import java.util.List;

public class UserSummaryMapper {
    public static UserSummary toUserSummary(UserSummaryResponse user) {
        return new UserSummary.Builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .numberCCCD(user.getNumberCCCD())
                .dateOfBirth(user.getDob())
                .phoneNumber(user.getPhoneNumber())
                .nationality(user.getNationality())
                .email(user.getEmail())
                .address(user.getAddress())
                .build();
    }

    public static List<UserSummary> toUserSummaryList(List<UserSummaryResponse> dtoList) {
        List<UserSummary> result = new ArrayList<>();
        for (UserSummaryResponse dto : dtoList) {
            result.add(toUserSummary(dto));
        }
        return result;
    }
}
