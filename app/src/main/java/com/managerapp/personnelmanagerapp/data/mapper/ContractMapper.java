package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.domain.model.Contract;

import java.util.ArrayList;
import java.util.List;

public class ContractMapper {
    public static Contract toContract(ContractResponse response) {
        return new Contract.Builder()
                .id(response.getId())
                .startDate(response.getStartDate())
                .endDate(response.getEndDate())
                .basicSalary(response.getBasicSalary())
                .clause(response.getClause())
                .contractStatusEnum(response.getContractStatusEnum())
                .contractTypeName(response.getContractTypeName())
                .user(UserSummaryMapper.toUserSummary(response.getUser()))
                .signer(UserSummaryMapper.toUserSummary(response.getSigner()))
                .positionName(response.getPositionName())
                .departmentName(response.getDepartmentName())
                .jobGradeCoefficient(response.getJobGradeCoefficient())
                .build();
    }

    public static List<Contract> toContracts(List<ContractResponse> responses) {
        List<Contract> contracts = new ArrayList<>();
        for (ContractResponse response : responses) {
            contracts.add(toContract(response));
        }
        return contracts;
    }
}
