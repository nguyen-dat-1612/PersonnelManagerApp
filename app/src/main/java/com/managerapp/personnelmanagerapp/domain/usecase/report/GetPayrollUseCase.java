package com.managerapp.personnelmanagerapp.domain.usecase.report;

import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;
import com.managerapp.personnelmanagerapp.domain.model.Payroll;
import com.managerapp.personnelmanagerapp.domain.repository.ReportRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetPayrollUseCase {
    private final ReportRepository reportRepository;
    @Inject
    public GetPayrollUseCase(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Single<List<Payroll>> execute(String startDate, String endDate) {
        return reportRepository.getPayroll(startDate, endDate);
    }
}
