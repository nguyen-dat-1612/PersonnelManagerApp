package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.JobGradeResponse;
import com.managerapp.personnelmanagerapp.domain.model.JobGrade;

import java.util.List;
import java.util.stream.Collectors;

public class JobGradeMapper {
    public static JobGrade toJobGrade(JobGradeResponse response) {
        return new JobGrade(
                response.getId(),
                response.getName(),
                response.getCoefficient(),
                response.getDescription()
        );
    }

    public static List<JobGrade> toJobGrade(List<JobGradeResponse> responses) {
        return responses.stream()
                .map(JobGradeMapper::toJobGrade)
                .collect(Collectors.toList());
    }
}
