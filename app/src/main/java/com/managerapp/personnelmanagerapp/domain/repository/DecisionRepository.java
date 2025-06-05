package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.request.DecisionApproveRequest;
import com.managerapp.personnelmanagerapp.data.remote.request.DecisionRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface DecisionRepository {
    Single<List<DecisionResponse>> getDecisions();
    Single<DecisionResponse> getDecisionById(String id);
    Single<DecisionResponse> createDecision(DecisionRequest decisionRequest);

    Observable<List<DecisionResponse>> getAllDecisions(String type);

    Single<DecisionResponse> updateDecision(String id);

    Maybe<Boolean> deleteDecision(String id);
}
