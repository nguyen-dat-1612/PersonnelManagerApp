package com.managerapp.personnelmanagerapp.data.repository;

import com.google.gson.Gson;
import com.managerapp.personnelmanagerapp.data.remote.api.ContractApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.domain.repository.ContractRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class ContractRepositoryImpl implements ContractRepository {
    private final ContractApiService apiService;
    private final Gson gson = new Gson();
    private static final String TAG = "ContractRepository";

    @Inject
    public ContractRepositoryImpl(ContractApiService apiService) {
        this.apiService = apiService;
    }

    public Single<List<ContractResponse>> getContracts(long userId) {
        return RxResultHandler.handle(apiService.getContracts(userId))
                .map(response -> response.getContent());
    }

    public Single<ContractResponse> getContractByID(int contractId) {
        return RxResultHandler.handle(apiService.getContractById(contractId));
    }
}
