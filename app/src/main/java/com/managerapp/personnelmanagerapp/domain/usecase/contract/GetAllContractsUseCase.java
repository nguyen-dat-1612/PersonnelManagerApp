package com.managerapp.personnelmanagerapp.domain.usecase.contract;

import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.domain.repository.ContractRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetAllContractsUseCase {
    private final ContractRepository contractRepository;

    @Inject
    public GetAllContractsUseCase(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Single<List<Contract>> execute() {
        return contractRepository.getContracts();
    }
}
