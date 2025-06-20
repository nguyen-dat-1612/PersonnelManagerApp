package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.model.Decision;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DecisionMapper {
    public static Decision toDecision(DecisionResponse decisionResponse) {
        return new Decision.Builder()
                .id(decisionResponse.getId())
                .attachment(decisionResponse.getAttachment())
                .content(decisionResponse.getContent())
                .value(decisionResponse.getValue())
                .type(decisionResponse.getType())
                .date(decisionResponse.getDate())
                .signer(Optional.ofNullable(decisionResponse.getSigner())
                        .map(UserSummaryMapper::toUserSummary)
                        .orElse(null))
                .user(Optional.ofNullable(decisionResponse.getUser())
                        .map(UserSummaryMapper::toUserSummary)
                        .orElse(null))
                .seniorityAllowanceRule(Optional.ofNullable(decisionResponse.getSeniorityAllowanceRule())
                        .map(SeniorityAllowanceRuleMapper::toSeniorityAllowanceRule)
                        .orElse(null)
                ).salaryPromotion(Optional.ofNullable(decisionResponse.getSalaryPromotion())
                        .map(SalaryPromotionMapper::toSalaryPromotion)
                        .orElse(null))
                .position(Optional.ofNullable(decisionResponse.getPosition())
                        .map(PositionMapper::toPosition)
                        .orElse(null))
                .build();
    }

    public static List<Decision> toDecisions(List<DecisionResponse> responses) {
        List<Decision> decisions = new ArrayList<>();
        for (DecisionResponse response : responses) {
            decisions.add(toDecision(response));
        }
        return decisions;
    }
}
