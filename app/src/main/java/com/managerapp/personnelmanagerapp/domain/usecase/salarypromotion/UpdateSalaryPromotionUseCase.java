package com.managerapp.personnelmanagerapp.domain.usecase.salarypromotion;

import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.domain.repository.SalaryPromotionRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class UpdateSalaryPromotionUseCase {
    private final SalaryPromotionRepository salaryPromotionRepository;

    @Inject
    public UpdateSalaryPromotionUseCase(SalaryPromotionRepository salaryPromotionRepository) {
        this.salaryPromotionRepository = salaryPromotionRepository;
    }


    public Single<SalaryPromotion> execute(int id, FormStatusEnum formStatus, String reason) {
        return salaryPromotionRepository.updateSalaryPromotion(id, formStatus, reason);
    }
}
