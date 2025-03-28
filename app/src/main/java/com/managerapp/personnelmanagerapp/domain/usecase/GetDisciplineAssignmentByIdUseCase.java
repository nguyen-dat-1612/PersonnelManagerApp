package com.managerapp.personnelmanagerapp.domain.usecase;

import com.managerapp.personnelmanagerapp.data.repository.DisciplineAssignmentRepository;
import com.managerapp.personnelmanagerapp.domain.model.DisciplineAssignment;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetDisciplineAssignmentByIdUseCase {
    private final DisciplineAssignmentRepository repository;

    @Inject
    public GetDisciplineAssignmentByIdUseCase(DisciplineAssignmentRepository repository) {
        this.repository = repository;
    }

    Single<DisciplineAssignment> excute(int userId, int disciplineId) {
        return repository.getDisciplineAssignmentById(userId, disciplineId);
    }
}
