package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.managerapp.personnelmanagerapp.data.remote.api.ContractApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractListResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class ContractRepository {
    private final ContractApiService apiService;
    private final Gson gson = new Gson();
    private static final String TAG = "ContractRepository";

    @Inject
    public ContractRepository(ContractApiService apiService) {
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
