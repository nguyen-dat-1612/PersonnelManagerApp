package com.managerapp.personnelmanagerapp.domain.usecase.decision;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.data.repository.DecisionRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.DecisionRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetDecisionByIdUseCase {
    private final DecisionRepository decisionRepository;

    @Inject
    public GetDecisionByIdUseCase(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public Single<DecisionResponse> execute(String id) {
        return decisionRepository.getDecisionById(id);
    }
}
