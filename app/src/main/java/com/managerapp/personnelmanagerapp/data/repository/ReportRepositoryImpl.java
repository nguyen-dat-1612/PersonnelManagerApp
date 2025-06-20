package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.mapper.ContractExpireReportMapper;
import com.managerapp.personnelmanagerapp.data.mapper.PayrollMapper;
import com.managerapp.personnelmanagerapp.data.remote.api.ReportApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.ContractExpireReport;
import com.managerapp.personnelmanagerapp.domain.model.Payroll;
import com.managerapp.personnelmanagerapp.domain.repository.ReportRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class ReportRepositoryImpl implements ReportRepository {
    private final ReportApiService reportApiService;
    private final RxResultHandler rxResultHandler;
    @Inject
    public ReportRepositoryImpl(ReportApiService reportApiService, RxResultHandler rxResultHandler) {
        this.reportApiService = reportApiService;
        this.rxResultHandler = rxResultHandler;
    }
    @Override
    public Single<List<Payroll>> getPayroll(String startDate, String endDate) {
        return rxResultHandler.handleSingle(reportApiService.getPayroll(startDate, endDate))
                .map(PayrollMapper::toPayroll);
    }

    @Override
    public Single<List<ContractExpireReport>> getContractExpireReport(int days) {
        return rxResultHandler.handleSingle(reportApiService.getContractExpireReport(days))
                .map(ContractExpireReportMapper::toContractExpireReport);
    }
}
