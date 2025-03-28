package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.domain.model.Contract;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ContractApiService {

    @GET("contracts/{id}")
    Single<Response<BaseResponse<List<Contract>>>> getContracts(@Path("id") int userId);

    @GET("contracts/{id}")
    Single<Response<BaseResponse<Contract>>> getContractById(@Path("id") String contractId);
}
