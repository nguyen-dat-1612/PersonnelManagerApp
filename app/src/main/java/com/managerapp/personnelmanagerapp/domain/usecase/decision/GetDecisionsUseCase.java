package com.managerapp.personnelmanagerapp.domain.usecase.decision;
import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.domain.repository.DecisionRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetDecisionsUseCase {
    private final DecisionRepository decisionRepository;

    @Inject
    public GetDecisionsUseCase(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public Single<List<Decision>> execute() {
        return decisionRepository.getDecisions();
    }

}
