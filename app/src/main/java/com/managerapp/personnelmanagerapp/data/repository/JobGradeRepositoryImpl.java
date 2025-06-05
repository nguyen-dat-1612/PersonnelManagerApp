package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.JobGradeApiService;
import com.managerapp.personnelmanagerapp.data.remote.response.JobGradeResponse;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.repository.JobGradeRepository;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Single;

public class JobGradeRepositoryImpl  implements JobGradeRepository {
    private final JobGradeApiService jobGradeApiService;
    private final LocalDataManager localDataManager;

    @Inject
    public JobGradeRepositoryImpl(JobGradeApiService jobGradeApiService, LocalDataManager localDataManager) {
        this.jobGradeApiService = jobGradeApiService;
        this.localDataManager = localDataManager;
    }

    @Override
    public Single<List<JobGradeResponse>> getAllJobGrades() {
        return RxResultHandler.handle(jobGradeApiService.getAllJobGrades());
    }

    @Override
    public Single<JobGradeResponse> getJobGradeById() {
        return localDataManager.getUserIdAsync()
                .flatMap(userId -> jobGradeApiService.getJobGradeById(userId))
                .map(response -> response.getData());
    }
}
