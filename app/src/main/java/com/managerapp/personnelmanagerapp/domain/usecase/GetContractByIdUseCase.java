package com.managerapp.personnelmanagerapp.domain.usecase;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.data.repository.ContractRepository;
import com.managerapp.personnelmanagerapp.domain.model.Contract;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetContractByIdUseCase {
    private final ContractRepository contractRepository;

    @Inject
    public GetContractByIdUseCase(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Single<ContractResponse> execute(int contractId) {
        return contractRepository.getContractByID(contractId);
    }
}
