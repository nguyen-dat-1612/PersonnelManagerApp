package com.managerapp.personnelmanagerapp.domain.usecase.seniority;
import com.managerapp.personnelmanagerapp.domain.model.SeniorityAllowanceRule;
import com.managerapp.personnelmanagerapp.domain.repository.SeniorityAllowanceRuleRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetSeniorityAllowanceRuleUseCase {
    private final SeniorityAllowanceRuleRepository seniorityAllowanceRuleRepository;

    @Inject
    public GetSeniorityAllowanceRuleUseCase(SeniorityAllowanceRuleRepository seniorityAllowanceRuleRepository) {
        this.seniorityAllowanceRuleRepository = seniorityAllowanceRuleRepository;
    }

    public Observable<List<SeniorityAllowanceRule>> execute(String fetchStatus) {
        return seniorityAllowanceRuleRepository.getSeniorityAllowanceRules(fetchStatus);
    }
}
