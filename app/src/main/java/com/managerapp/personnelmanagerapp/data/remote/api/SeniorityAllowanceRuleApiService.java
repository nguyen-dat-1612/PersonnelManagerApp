package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.SeniorityAllowanceRuleResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SeniorityAllowanceRuleApiService {
    @GET("seniority-allowance-rules")
    Observable<BaseResponse<List<SeniorityAllowanceRuleResponse>>> getSeniorityAllowanceRules(@Query("fetchStatus") String fetchStatus);
}
