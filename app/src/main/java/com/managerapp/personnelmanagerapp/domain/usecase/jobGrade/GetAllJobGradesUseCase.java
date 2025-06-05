package com.managerapp.personnelmanagerapp.domain.usecase.jobGrade;

import com.managerapp.personnelmanagerapp.data.remote.response.JobGradeResponse;
import com.managerapp.personnelmanagerapp.domain.repository.JobGradeRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetAllJobGradesUseCase {
    private final JobGradeRepository jobGradeRepository;

    @Inject
    public GetAllJobGradesUseCase(JobGradeRepository jobGradeRepository) {
        this.jobGradeRepository = jobGradeRepository;
    }

    public Single<List<JobGradeResponse>> execute() {
        return jobGradeRepository.getAllJobGrades();
    }
}
