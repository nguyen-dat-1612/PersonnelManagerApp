package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.DecisionApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.DecisionApproveRequest;
import com.managerapp.personnelmanagerapp.data.remote.request.DecisionRequest;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
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

    @Inject
    public DecisionRepositoryImpl(DecisionApiService apiService, LocalDataManager localDataManager) {
        this.apiService = apiService;
        this.localDataManager = localDataManager;
    }

    public Single<List<DecisionResponse>> getDecisions() {
        return localDataManager.getUserIdAsync()
                .flatMap( userId -> RxResultHandler.handle(apiService.getAllDecisionsByUserId(userId)));
    }

    public Single<DecisionResponse> getDecisionById(String id) {
        return RxResultHandler.handle(apiService.getDecisionById(id));
    }

    @Override
    public Single<DecisionResponse> createDecision(DecisionRequest decisionRequest) {
        return RxResultHandler.handle(apiService.createDecision(decisionRequest));
    }

    @Override
    public Observable<List<DecisionResponse>> getAllDecisions(String type) {
        return apiService.getAllDecisions(type)
                .map(response -> response.getData())
                .onErrorResumeNext(throwable -> {
                    return Observable.error(new Throwable(throwable.getMessage()));
                });
    }

    @Override
    public Single<DecisionResponse> updateDecision(String id) {
        return localDataManager.getUserIdAsync()
                .flatMap( userId ->RxResultHandler.handle(apiService.updateDecision(id, new DecisionApproveRequest(null, userId))));
    }

    @Override
    public Maybe<Boolean> deleteDecision(String id) {
        return apiService.deleteDecision(id)
                .map(apiResponse -> apiResponse.getCode() == 200);
    }

}
