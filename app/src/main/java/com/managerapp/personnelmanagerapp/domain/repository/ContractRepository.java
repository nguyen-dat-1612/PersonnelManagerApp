package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.Contract;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ContractRepository {
    Single<List<Contract>> getContracts();
    Single<Contract> getContractByID(int contractId);
}
