package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.mapper.JobGradeMapper;
import com.managerapp.personnelmanagerapp.data.remote.api.JobGradeApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.JobGrade;
import com.managerapp.personnelmanagerapp.domain.repository.JobGradeRepository;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Single;

public class JobGradeRepositoryImpl  implements JobGradeRepository {
    private final JobGradeApiService jobGradeApiService;
    private final LocalDataManager localDataManager;
    private final RxResultHandler rxResultHandler;

    @Inject
    public JobGradeRepositoryImpl(JobGradeApiService jobGradeApiService, LocalDataManager localDataManager, RxResultHandler rxResultHandler) {
        this.jobGradeApiService = jobGradeApiService;
        this.localDataManager = localDataManager;
        this.rxResultHandler = rxResultHandler;
    }

    @Override
    public Single<List<JobGrade>> getAllJobGrades() {
        return rxResultHandler.handleSingle(jobGradeApiService.getAllJobGrades())
                .map(JobGradeMapper::toJobGrade);
    }

    @Override
    public Single<JobGrade> getJobGradeById() {
        return localDataManager.getUserIdAsync()
                .flatMap(userId ->
                        rxResultHandler.handleSingle(jobGradeApiService.getJobGradeById(userId))
                )
                .map(JobGradeMapper::toJobGrade);
    }
}
