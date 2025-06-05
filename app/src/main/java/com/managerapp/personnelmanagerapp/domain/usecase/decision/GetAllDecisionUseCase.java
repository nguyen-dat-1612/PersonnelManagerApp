package com.managerapp.personnelmanagerapp.domain.usecase.decision;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.repository.DecisionRepository;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Observable;

public class GetAllDecisionUseCase {
    private final DecisionRepository decisionRepository;

    @Inject
    public GetAllDecisionUseCase(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    public Observable<List<DecisionResponse>> execute(String type) {
        return decisionRepository.getAllDecisions(type);
    }
}
