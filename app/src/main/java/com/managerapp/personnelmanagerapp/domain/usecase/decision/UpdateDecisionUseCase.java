package com.managerapp.personnelmanagerapp.domain.usecase.decision;

import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.domain.repository.DecisionRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class UpdateDecisionUseCase {
    private final DecisionRepository decisionRepository;

    @Inject
    public UpdateDecisionUseCase(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public Single<Decision> execute(String id, String attachment) {
        return decisionRepository.updateDecision(id, attachment);
    }
}
