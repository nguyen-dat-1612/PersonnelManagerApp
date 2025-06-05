package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.response.SeniorityAllowanceRuleResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface SeniorityAllowanceRuleRepository {
    Observable<List<SeniorityAllowanceRuleResponse>> getSeniorityAllowanceRules(String fetchStatus);
}
