package com.managerapp.personnelmanagerapp.domain.usecase;

import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.data.repository.DisciplineAssignmentRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetDisciplineAssignmentsUseCase {
    private final DisciplineAssignmentRepository repository;

    @Inject
    public GetDisciplineAssignmentsUseCase(DisciplineAssignmentRepository repository) {
        this.repository = repository;
    }

    public Single<List<AssignmentResponse>> execute(int userId) {
        return repository.getDisciplineAssignments(userId);
    }

}
