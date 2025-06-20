package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.mapper.DecisionMapper;
import com.managerapp.personnelmanagerapp.data.remote.api.DecisionApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.DecisionApproveRequest;
import com.managerapp.personnelmanagerapp.data.remote.request.DecisionRequest;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.domain.repository.DecisionRepository;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class DecisionRepositoryImpl implements DecisionRepository {
    private final DecisionApiService apiService;
    private final LocalDataManager localDataManager;
    private final RxResultHandler rxResultHandler;

    @Inject
    public DecisionRepositoryImpl(DecisionApiService apiService, LocalDataManager localDataManager, com.managerapp.personnelmanagerapp.data.utils.RxResultHandler rxResultHandler) {
        this.apiService = apiService;
        this.localDataManager = localDataManager;
        this.rxResultHandler = rxResultHandler;
    }

    public Single<List<Decision>> getDecisions() {
        return localDataManager.getUserIdAsync()
                .flatMap( userId ->
                        rxResultHandler.handleSingle(apiService.getAllDecisionsByUserId(userId))
                                .map(DecisionMapper::toDecisions)
                );
    }

    public Single<Decision> getDecisionById(String id) {
        return rxResultHandler.handleSingle(apiService.getDecisionById(id))
                .map(DecisionMapper::toDecision);
    }

    @Override
    public Single<Decision> createDecision(DecisionRequest decisionRequest) {
        return rxResultHandler.handleSingle(apiService.createDecision(decisionRequest))
                .map(DecisionMapper::toDecision);
    }

    @Override
    public Observable<List<Decision>> getAllDecisions(String type) {
        return apiService.getAllDecisions(type)
                .map(response -> {
                    List<DecisionResponse> decisionResponses = response.getData();
                    return DecisionMapper.toDecisions(decisionResponses);
                })
                .onErrorResumeNext(throwable ->
                    Observable.error(new Throwable(throwable.getMessage()))
                );
    }

    @Override
    public Single<Decision> updateDecision(String id, String attachment) {

        return localDataManager.getUserIdAsync()
                .flatMap( userId ->
                        rxResultHandler.handleSingle(
                                apiService.updateDecision(id, new DecisionApproveRequest(attachment, userId))
                        ).map(DecisionMapper::toDecision)
                );
    }

    @Override
    public Maybe<Boolean> deleteDecision(String id) {
        return apiService.deleteDecision(id)
                .map(apiResponse -> apiResponse.getCode() == 200);
    }

}
