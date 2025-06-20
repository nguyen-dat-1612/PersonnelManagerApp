package com.managerapp.personnelmanagerapp.domain.usecase.contract;

import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.domain.repository.ContractRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetContractByIdUseCase {
    private final ContractRepository contractRepository;

    @Inject
    public GetContractByIdUseCase(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Single<Contract> execute(int contractId) {
        return contractRepository.getContractByID(contractId);
    }
}
