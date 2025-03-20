package com.managerapp.personnelmanagerapp.data.remote;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.domain.model.Contract;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ContractApiService {

    @GET("contracts")
    Single<Response<ContractResponse<List<Contract>>>> getContracts();

    @GET("contracts/{id}")
    Single<Response<ContractResponse<Contract>>> getContractById(@Path("id") String contractId);
}
