package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.SalaryPromotionResponse;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;

import java.util.List;
import java.util.stream.Collectors;

public class SalaryPromotionMapper {
    public static SalaryPromotion toSalaryPromotion(SalaryPromotionResponse salaryPromotionResponse) {
        return new SalaryPromotion.Builder()
                .id(salaryPromotionResponse.getId())
                .date(salaryPromotionResponse.getDate())
                .status(salaryPromotionResponse.getStatus())
                .note(salaryPromotionResponse.getNote())
                .reason(salaryPromotionResponse.getReason())
                .userName(salaryPromotionResponse.getUserName())
                .signerName(salaryPromotionResponse.getSignerName())
                .currentJobGradeName(salaryPromotionResponse.getCurrentJobGradeName())
                .requestJobGradeName(salaryPromotionResponse.getRequestJobGradeName())
                .requestJobGradeValue(salaryPromotionResponse.getRequestJobGradeValue())
                .build();
    }

    public static List<SalaryPromotion> toSalaryPromotion(List<SalaryPromotionResponse> salaryPromotionResponses) {
        return salaryPromotionResponses.stream()
                .map(SalaryPromotionMapper::toSalaryPromotion)
                .collect(Collectors.toList());
    }
}
