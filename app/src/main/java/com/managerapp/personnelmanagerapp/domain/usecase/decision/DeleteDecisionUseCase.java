package com.managerapp.personnelmanagerapp.domain.usecase.decision;

import com.managerapp.personnelmanagerapp.domain.repository.DecisionRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Maybe;

public class DeleteDecisionUseCase {
    private final DecisionRepository decisionRepository;

    @Inject
    public DeleteDecisionUseCase(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public Maybe<Boolean> execute(String id) {
        return decisionRepository.deleteDecision(id);
    }
}
