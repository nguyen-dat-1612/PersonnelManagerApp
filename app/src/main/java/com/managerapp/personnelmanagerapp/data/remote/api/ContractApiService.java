package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.domain.model.Contract;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ContractApiService {

    @GET("contracts/user/{userId}")
    Single<Response<BaseResponse<List<ContractResponse>>>> getContracts(@Path("userId") long userId);

    @GET("contracts/{contractId}")
    Single<Response<BaseResponse<ContractResponse>>> getContractById(@Path("contractId") int contractId);

}
