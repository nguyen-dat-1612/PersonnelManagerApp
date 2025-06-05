package com.managerapp.personnelmanagerapp.domain.usecase.jobGrade;

import com.managerapp.personnelmanagerapp.data.remote.response.JobGradeResponse;
import com.managerapp.personnelmanagerapp.domain.repository.JobGradeRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetJobGradeByIdUseCase {
    private final JobGradeRepository jobGradeRepository;

    @Inject
    public GetJobGradeByIdUseCase(JobGradeRepository jobGradeRepository) {
        this.jobGradeRepository = jobGradeRepository;
    }

    public Single<JobGradeResponse> execute() {
        return jobGradeRepository.getJobGradeById();
    }
}
