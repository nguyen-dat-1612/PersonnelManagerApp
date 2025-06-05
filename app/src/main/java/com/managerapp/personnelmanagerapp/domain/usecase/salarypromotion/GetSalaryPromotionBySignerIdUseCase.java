package com.managerapp.personnelmanagerapp.domain.usecase.salarypromotion;

import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.domain.repository.SalaryPromotionRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetSalaryPromotionBySignerIdUseCase {
    private final SalaryPromotionRepository salaryPromotionRepository;

    @Inject
    public GetSalaryPromotionBySignerIdUseCase(SalaryPromotionRepository salaryPromotionRepository) {
        this.salaryPromotionRepository = salaryPromotionRepository;
    }

    public Observable<List<SalaryPromotion>> execute(FormStatusEnum formStatus) {
        return salaryPromotionRepository.getSalaryPromotionsBySignerId(formStatus);
    }
}
