package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.SalaryPromotionApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.SalaryPromotionRequest;
import com.managerapp.personnelmanagerapp.data.remote.request.SalaryPromotionUpdateRequest;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.domain.repository.SalaryPromotionRepository;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import javax.inject.Inject;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class SalaryPromotionRepositoryImpl implements SalaryPromotionRepository {
    private static final Logger log = LoggerFactory.getLogger(SalaryPromotionRepositoryImpl.class);
    private final SalaryPromotionApiService salaryPromotionApiService;

    private final LocalDataManager localDataManager;
    @Inject
    public SalaryPromotionRepositoryImpl(SalaryPromotionApiService salaryPromotionApiService, LocalDataManager localDataManager) {
        this.salaryPromotionApiService = salaryPromotionApiService;
        this.localDataManager = localDataManager;
    }


    @Override
    public Observable<List<SalaryPromotion>> getPendingSalaryPromotions() {
        return salaryPromotionApiService.getPendingSalaryPromotions()
                .map(response -> response.getData())
                .onErrorReturnItem(List.of());
    }

    @Override
    public Single<SalaryPromotion> createSalaryPromotion(SalaryPromotionRequest salaryPromotionRequest) {
        return RxResultHandler.handle(salaryPromotionApiService.createSalaryPromotion(salaryPromotionRequest));
    }

    @Override
    public Maybe<Boolean> deleteSalaryPromotion(int id) {
        return salaryPromotionApiService.deleteSalaryPromotion(id)
                .map(response ->
                        response.getCode() == 200 ? true : false
                ).toMaybe()
                .onErrorReturn(
                        throwable -> false
                );
    }

    @Override
    public Single<SalaryPromotion> updateSalaryPromotion(int id, FormStatusEnum formStatus, String reason) {
        return localDataManager.getUserIdAsync()
                .flatMap(userId ->
                    RxResultHandler.handle(
                            salaryPromotionApiService.updateSalaryPromotion(id, new SalaryPromotionUpdateRequest(userId, formStatus, reason))
                    )
                );
    }

    @Override
    public Observable<List<SalaryPromotion>> getSalaryPromotionsBySignerId(FormStatusEnum formStatus) {
        return localDataManager.getUserIdAsync()
                .toObservable()
                .flatMap(userId ->
                        salaryPromotionApiService.getSalaryPromotionsBySignerId(userId,formStatus)
                )
                .map(response -> response.getData());
    }
}
