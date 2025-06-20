package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractExpireReportResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;
import com.managerapp.personnelmanagerapp.domain.model.ContractExpireReport;
import com.managerapp.personnelmanagerapp.domain.model.Payroll;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ReportRepository {
    Single<List<Payroll>> getPayroll(String startDate, String endDate);

    Single<List<ContractExpireReport>> getContractExpireReport(int days);
}
