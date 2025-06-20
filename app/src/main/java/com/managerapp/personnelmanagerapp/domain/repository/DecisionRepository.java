package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.request.DecisionRequest;
import com.managerapp.personnelmanagerapp.domain.model.Decision;
import java.util.List;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface DecisionRepository {
    Single<List<Decision>> getDecisions();
    Single<Decision> getDecisionById(String id);
    Single<Decision> createDecision(DecisionRequest decisionRequest);

    Observable<List<Decision>> getAllDecisions(String type);

    Single<Decision> updateDecision(String id, String attachment);

    Maybe<Boolean> deleteDecision(String id);
}
