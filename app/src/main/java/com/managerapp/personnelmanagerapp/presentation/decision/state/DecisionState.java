package com.managerapp.personnelmanagerapp.presentation.decision.state;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.domain.model.DecisionEnum;

import java.util.List;

public class DecisionState {
    public final int awardCount;
    public final int disciplineCount;
    public final int promotionCount;
    public final int salaryIncreaseCount;
    public final int seniorityCount;
    public final int terminationCount;

    private DecisionState(int awardCount, int disciplineCount, int promotionCount,
                          int salaryIncreaseCount, int seniorityCount, int terminationCount) {
        this.awardCount = awardCount;
        this.disciplineCount = disciplineCount;
        this.promotionCount = promotionCount;
        this.salaryIncreaseCount = salaryIncreaseCount;
        this.seniorityCount = seniorityCount;
        this.terminationCount = terminationCount;
    }

    public static DecisionState from(List<Decision> decisions) {
        int award = 0, discipline = 0, promo = 0, salary = 0, seniority = 0, termination = 0;

        for (Decision d : decisions) {
            switch (DecisionEnum.from(d.getType().toString()))   {
                case AWARD: award++; break;
                case DISCIPLINE: discipline++; break;
                case PROMOTION: promo++; break;
                case INCREASE_SALARY: salary++; break;
                case SENIORITY_ALLOWANCE: seniority++; break;
                case TERMINATION: termination++; break;
            }
        }

        return new DecisionState(award, discipline, promo, salary, seniority, termination);
    }
}
