package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.DecisionApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.repository.DecisionRepository;
import com.managerapp.personnelmanagerapp.utils.manager.LocalDataManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
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

}
