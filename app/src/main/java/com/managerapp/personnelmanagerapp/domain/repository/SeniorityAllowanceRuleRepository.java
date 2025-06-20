package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.response.SeniorityAllowanceRuleResponse;
import com.managerapp.personnelmanagerapp.domain.model.SeniorityAllowanceRule;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface SeniorityAllowanceRuleRepository {
    Observable<List<SeniorityAllowanceRule>> getSeniorityAllowanceRules(String fetchStatus);
}
