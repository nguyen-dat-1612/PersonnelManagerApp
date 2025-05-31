package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractExpireReportResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface ReportRepository {
    Single<List<PayrollResponse>> getPayroll(String startDate, String endDate);

    Single<List<ContractExpireReportResponse>> getContractExpireReport(int days);
}
