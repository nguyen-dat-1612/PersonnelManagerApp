package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.request.SalaryPromotionUpdateRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.SalaryPromotionResponse;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.data.remote.request.SalaryPromotionRequest;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SalaryPromotionApiService {
    @GET("salary-promotions/pending")
    Observable<BaseResponse<List<SalaryPromotionResponse>>> getPendingSalaryPromotions();

    @POST("salary-promotions/create")
    Single<BaseResponse<SalaryPromotionResponse>> createSalaryPromotion(@Body SalaryPromotionRequest salaryPromotionRequest);

    @DELETE("salary-promotions/{id}")
    Single<BaseResponse<Void>> deleteSalaryPromotion(@Path("id") int id);

    @PUT("salary-promotions/{id}")
    Single<BaseResponse<SalaryPromotionResponse>> updateSalaryPromotion(@Path("id") int id, @Body SalaryPromotionUpdateRequest salaryPromotionUpdateRequest);

    @GET("salary-promotions/signer/{signerId}")
    Observable<BaseResponse<List<SalaryPromotionResponse>>> getSalaryPromotionsBySignerId(@Path("signerId") long signerId, @Query("status") FormStatusEnum formStatus);

}
