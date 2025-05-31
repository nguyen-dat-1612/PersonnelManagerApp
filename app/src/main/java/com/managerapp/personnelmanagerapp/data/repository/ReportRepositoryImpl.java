package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.ReportApiService;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractExpireReportResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.repository.ReportRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class ReportRepositoryImpl implements ReportRepository {
    private final ReportApiService reportApiService;
    @Inject
    public ReportRepositoryImpl(ReportApiService reportApiService) {
        this.reportApiService = reportApiService;
    }
    @Override
    public Single<List<PayrollResponse>> getPayroll(String startDate, String endDate) {
        return RxResultHandler.handle(reportApiService.getPayroll(startDate, endDate));
    }

    @Override
    public Single<List<ContractExpireReportResponse>> getContractExpireReport(int days) {
        return RxResultHandler.handle(reportApiService.getContractExpireReport(days));
    }
}
