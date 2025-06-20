package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;
import com.managerapp.personnelmanagerapp.domain.model.Payroll;

import java.util.ArrayList;
import java.util.List;

public class PayrollMapper {
    public static Payroll toPayroll(PayrollResponse response) {
        return Payroll.builder()
                .actualWorkDays(response.getActualWorkDays())
                .unpaidLeaveDays(response.getUnpaidLeaveDays())
                .workDays(response.getWorkDays())
                .salary(response.getSalary())
                .seniority(response.getSeniority())
                .userId(response.getUserId())
                .fullName(response.getFullName())
                .build();
    }

    public static List<Payroll> toPayroll(List<PayrollResponse> responses) {
        List<Payroll> payrolls = new ArrayList<>();
        for (PayrollResponse response : responses) {
            payrolls.add(toPayroll(response));
        }
        return payrolls;
    }
}
