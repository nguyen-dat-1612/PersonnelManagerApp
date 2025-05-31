package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ContractRepository {
    Single<List<ContractResponse>> getContracts(long userId);
    Single<ContractResponse> getContractByID(int contractId);
}
