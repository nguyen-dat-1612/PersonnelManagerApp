package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.SeniorityAllowanceRuleApiService;
import com.managerapp.personnelmanagerapp.data.remote.response.SeniorityAllowanceRuleResponse;
import com.managerapp.personnelmanagerapp.domain.repository.SeniorityAllowanceRuleRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class SeniorityAllowanceRuleRepositoryImpl implements SeniorityAllowanceRuleRepository {

    private final SeniorityAllowanceRuleApiService seniorityAllowanceRuleApiService;

    @Inject
    public SeniorityAllowanceRuleRepositoryImpl(SeniorityAllowanceRuleApiService seniorityAllowanceRuleApiService) {
        this.seniorityAllowanceRuleApiService = seniorityAllowanceRuleApiService;
    }

    @Override
    public Observable<List<SeniorityAllowanceRuleResponse>> getSeniorityAllowanceRules(String fetchStatus) {
        return seniorityAllowanceRuleApiService
                .getSeniorityAllowanceRules(fetchStatus)
                .map(response -> response.getData())
                .onErrorResumeNext(throwable -> {
                    return Observable.error(new Throwable("Network error or custom message"));
                });
    }
}
