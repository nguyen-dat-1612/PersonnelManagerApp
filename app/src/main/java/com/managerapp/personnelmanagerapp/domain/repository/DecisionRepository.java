package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface DecisionRepository {
    Single<List<DecisionResponse>> getDecisions();
    Single<DecisionResponse> getDecisionById(String id);
}
