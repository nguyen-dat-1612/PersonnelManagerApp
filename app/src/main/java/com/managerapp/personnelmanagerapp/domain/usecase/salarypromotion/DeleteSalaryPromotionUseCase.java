package com.managerapp.personnelmanagerapp.domain.usecase.salarypromotion;

import com.managerapp.personnelmanagerapp.domain.repository.SalaryPromotionRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Maybe;

public class DeleteSalaryPromotionUseCase {
    private final SalaryPromotionRepository salaryPromotionRepository;

    @Inject
    public DeleteSalaryPromotionUseCase(SalaryPromotionRepository salaryPromotionRepository) {
        this.salaryPromotionRepository = salaryPromotionRepository;
    }

    public Maybe<Boolean> execute(int id) {
        return salaryPromotionRepository.deleteSalaryPromotion(id);
    }
}
