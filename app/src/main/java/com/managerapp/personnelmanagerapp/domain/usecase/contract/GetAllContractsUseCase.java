package com.managerapp.personnelmanagerapp.domain.usecase.contract;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.data.repository.ContractRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetAllContractsUseCase {
    private final ContractRepository contractRepository;

    @Inject
    public GetAllContractsUseCase(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Single<List<ContractResponse>> execute(long userId) {
        return contractRepository.getContracts(userId);
    }
}
