package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.domain.model.JobGrade;
import java.util.List;
import io.reactivex.rxjava3.core.Single;

public interface JobGradeRepository {

    Single<List<JobGrade>> getAllJobGrades();

    Single<JobGrade> getJobGradeById();

}
