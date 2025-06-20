package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.mapper.ContractMapper;
import com.managerapp.personnelmanagerapp.data.remote.api.ContractApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.domain.repository.ContractRepository;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Single;

public class ContractRepositoryImpl implements ContractRepository {
    private final ContractApiService apiService;
    private final RxResultHandler rxResultHandler;
    private final LocalDataManager localDataManager;

    @Inject
    public ContractRepositoryImpl(ContractApiService apiService, RxResultHandler rxResultHandler, LocalDataManager localDataManager) {
        this.apiService = apiService;
        this.rxResultHandler = rxResultHandler;
        this.localDataManager = localDataManager;
    }

    public Single<List<Contract>> getContracts() {
        return localDataManager.getUserIdAsync()
                .flatMap(userId -> rxResultHandler.handleSingle(apiService.getContracts(userId)))
                .map(response ->
                    ContractMapper.toContracts(response.getContent())
                );
    }


    public Single<Contract> getContractByID(int contractId) {
        return rxResultHandler.handleSingle(apiService.getContractById(contractId))
                .map(ContractMapper::toContract);
    }
}
