package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.SeniorityAllowanceRuleResponse;
import com.managerapp.personnelmanagerapp.domain.model.SeniorityAllowanceRule;

import java.util.List;
import java.util.stream.Collectors;

public class SeniorityAllowanceRuleMapper {
    public static SeniorityAllowanceRule toSeniorityAllowanceRule(SeniorityAllowanceRuleResponse seniorityAllowanceRuleResponse) {
        return new SeniorityAllowanceRule.Builder()
                .id(seniorityAllowanceRuleResponse.getId())
                .minService(seniorityAllowanceRuleResponse.getMinService())
                .seniorityPercentage(seniorityAllowanceRuleResponse.getSeniorityPercentage())
                .seniorityLeaveDay(seniorityAllowanceRuleResponse.getSeniorityLeaveDay())
                .effectiveDate(seniorityAllowanceRuleResponse.getEffectiveDate())
                .expiryDate(seniorityAllowanceRuleResponse.getExpiryDate())
                .description(seniorityAllowanceRuleResponse.getDescription())
                .signer(UserSummaryMapper.toUserSummary(seniorityAllowanceRuleResponse.getSigner()))
                .build();

    }

    public static List<SeniorityAllowanceRule> toSeniorityAllowanceRule(List<SeniorityAllowanceRuleResponse> seniorityAllowanceRuleResponses) {
        return seniorityAllowanceRuleResponses.stream()
                .map(SeniorityAllowanceRuleMapper::toSeniorityAllowanceRule)
                .collect(Collectors.toList());
    }
}
