package com.managerapp.personnelmanagerapp.domain.usecase.decision;

import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.domain.repository.DecisionRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetDecisionByIdUseCase {
    private final DecisionRepository decisionRepository;

    @Inject
    public GetDecisionByIdUseCase(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public Single<Decision> execute(String id) {
        return decisionRepository.getDecisionById(id);
    }
}
