package com.managerapp.personnelmanagerapp.domain.usecase.decision;

import com.managerapp.personnelmanagerapp.data.remote.request.DecisionApproveRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.repository.DecisionRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class UpdateDecisionUseCase {
    private final DecisionRepository decisionRepository;

    @Inject
    public UpdateDecisionUseCase(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public Single<DecisionResponse> execute(String id) {
        return decisionRepository.updateDecision(id);
    }
}
