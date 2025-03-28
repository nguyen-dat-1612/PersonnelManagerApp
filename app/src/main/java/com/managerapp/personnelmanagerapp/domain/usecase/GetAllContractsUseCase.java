package com.managerapp.personnelmanagerapp.domain.usecase;

import com.managerapp.personnelmanagerapp.data.repository.ContractRepository;
import com.managerapp.personnelmanagerapp.domain.model.Contract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetAllContractsUseCase {
    private final ContractRepository contractRepository;

    @Inject
    public GetAllContractsUseCase(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Single<List<Contract>> execute(int userId) {
        return contractRepository.getContracts(userId);
    }
}
