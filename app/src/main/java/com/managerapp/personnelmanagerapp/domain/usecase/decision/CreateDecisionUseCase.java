package com.managerapp.personnelmanagerapp.domain.usecase.decision;

import com.managerapp.personnelmanagerapp.data.remote.request.DecisionRequest;
import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.domain.repository.DecisionRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class CreateDecisionUseCase {
    private final DecisionRepository decisionRepository;

    @Inject
    public CreateDecisionUseCase(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public Single<Decision> execute(DecisionRequest decisionRequest) {
        return decisionRepository.createDecision(decisionRequest);
    }
}
