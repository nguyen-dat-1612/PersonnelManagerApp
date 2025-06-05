package com.managerapp.personnelmanagerapp.domain.usecase.salarypromotion;

import com.managerapp.personnelmanagerapp.data.remote.request.SalaryPromotionRequest;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.domain.repository.SalaryPromotionRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class CreateSalaryPromotionUseCase {
    private final SalaryPromotionRepository salaryPromotionRepository;

    @Inject
    public CreateSalaryPromotionUseCase(SalaryPromotionRepository salaryPromotionRepository) {
        this.salaryPromotionRepository = salaryPromotionRepository;
    }

    public Single<SalaryPromotion> execute(SalaryPromotionRequest salaryPromotionRequest) {
        return salaryPromotionRepository.createSalaryPromotion(salaryPromotionRequest);
    }
}
