package com.managerapp.personnelmanagerapp.domain.usecase.decision;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.data.repository.DecisionRepository;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetDecisionsUseCase {
    private final DecisionRepository decisionRepository;

    @Inject
    public GetDecisionsUseCase(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public Single<List<DecisionResponse>> execute(long userId) {
        return decisionRepository.getDecisions(userId);
    }

}
