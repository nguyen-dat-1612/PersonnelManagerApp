package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.mapper.SalaryPromotionMapper;
import com.managerapp.personnelmanagerapp.data.remote.api.SalaryPromotionApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.SalaryPromotionRequest;
import com.managerapp.personnelmanagerapp.data.remote.request.SalaryPromotionUpdateRequest;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.domain.repository.SalaryPromotionRepository;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import java.util.List;
import javax.inject.Inject;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class SalaryPromotionRepositoryImpl implements SalaryPromotionRepository {
    private final SalaryPromotionApiService salaryPromotionApiService;

    private final RxResultHandler rxResultHandler;
    private final LocalDataManager localDataManager;
    @Inject
    public SalaryPromotionRepositoryImpl(SalaryPromotionApiService salaryPromotionApiService, com.managerapp.personnelmanagerapp.data.utils.RxResultHandler rxResultHandler, LocalDataManager localDataManager) {
        this.salaryPromotionApiService = salaryPromotionApiService;
        this.rxResultHandler = rxResultHandler;
        this.localDataManager = localDataManager;
    }


    @Override
    public Observable<List<SalaryPromotion>> getPendingSalaryPromotions() {
        return salaryPromotionApiService.getPendingSalaryPromotions()
                .map(response -> SalaryPromotionMapper.toSalaryPromotion(response.getData()))
                .onErrorReturnItem(List.of());
    }

    @Override
    public Single<SalaryPromotion> createSalaryPromotion(SalaryPromotionRequest salaryPromotionRequest) {
        return rxResultHandler.handleSingle(salaryPromotionApiService.createSalaryPromotion(salaryPromotionRequest))
                .map(SalaryPromotionMapper::toSalaryPromotion);
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
                        rxResultHandler.handleSingle(
                            salaryPromotionApiService.updateSalaryPromotion(id, new SalaryPromotionUpdateRequest(userId, formStatus, reason))
                    ).map(SalaryPromotionMapper::toSalaryPromotion)
                );
    }

    @Override
    public Observable<List<SalaryPromotion>> getSalaryPromotionsBySignerId(FormStatusEnum formStatus) {
        return localDataManager.getUserIdAsync()
                .toObservable()
                .flatMap(userId ->
                        salaryPromotionApiService.getSalaryPromotionsBySignerId(userId,formStatus)
                )
                .map(response -> SalaryPromotionMapper.toSalaryPromotion(response.getData()));
    }
}
