package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractExpireReportResponse;
import com.managerapp.personnelmanagerapp.domain.model.ContractExpireReport;
import com.managerapp.personnelmanagerapp.domain.model.ContractStatusEnum;

import java.util.ArrayList;
import java.util.List;

public class ContractExpireReportMapper {
    public static ContractExpireReport toContractExpireReport(ContractExpireReportResponse response) {
        return ContractExpireReport.builder()
                .stt(response.getStt())
                .fullName(response.getFullName())
                .email(response.getEmail())
                .departmentName(response.getDepartmentName())
                .positionName(response.getPositionName())
                .contractTypeName(response.getContractTypeName())
                .endDate(response.getEndDate())
                .contractStatus(response.getContractStatus())
                .remainingDays(response.getRemainingDays())
                .build();
    }

    public static List<ContractExpireReport> toContractExpireReport(List<ContractExpireReportResponse> responses) {
        List<ContractExpireReport> contractExpireReports = new ArrayList<>();
        for (ContractExpireReportResponse response : responses) {
            contractExpireReports.add(toContractExpireReport(response));
        }
        return contractExpireReports;
    }
}
