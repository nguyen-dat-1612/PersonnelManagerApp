package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractExpireReportResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReportApiService {
    @GET("reports/payroll")
    Single<BaseResponse<List<PayrollResponse>>> getPayroll(@Query("startDate") String startDate, @Query("endDate") String endDate);


    @GET("reports/contracts/expiring")
    Single<BaseResponse<List<ContractExpireReportResponse>>> getContractExpireReport(@Query("days") int days);


}
