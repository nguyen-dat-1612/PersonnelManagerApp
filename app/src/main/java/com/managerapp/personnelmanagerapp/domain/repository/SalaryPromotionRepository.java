package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.request.SalaryPromotionRequest;
import com.managerapp.personnelmanagerapp.data.remote.request.SalaryPromotionUpdateRequest;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface SalaryPromotionRepository {
    Observable<List<SalaryPromotion>> getPendingSalaryPromotions();
    Single<SalaryPromotion> createSalaryPromotion(SalaryPromotionRequest salaryPromotionRequest);
    Maybe<Boolean> deleteSalaryPromotion(int id);
    Single<SalaryPromotion> updateSalaryPromotion(int id, FormStatusEnum formStatus, String reason);
    Observable<List<SalaryPromotion>> getSalaryPromotionsBySignerId(FormStatusEnum formStatus);
}
