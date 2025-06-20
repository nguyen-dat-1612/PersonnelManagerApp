package com.managerapp.personnelmanagerapp.domain.usecase.report;

import com.managerapp.personnelmanagerapp.domain.model.ContractExpireReport;
import com.managerapp.personnelmanagerapp.domain.repository.ReportRepository;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Single;

public class GetContractExpireReportUseCase {
    private final ReportRepository reportRepository;
    @Inject
    public GetContractExpireReportUseCase(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
    public Single<List<ContractExpireReport>> execute(int days) {
        return reportRepository.getContractExpireReport(days);
    }
}
