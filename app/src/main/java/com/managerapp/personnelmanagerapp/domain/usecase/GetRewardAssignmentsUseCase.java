package com.managerapp.personnelmanagerapp.domain.usecase;

import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.data.repository.RewardAssignmentRepository;
import com.managerapp.personnelmanagerapp.domain.model.RewardAssignment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetRewardAssignmentsUseCase {
    private final RewardAssignmentRepository repository;

    @Inject
    public GetRewardAssignmentsUseCase(RewardAssignmentRepository repository) {
        this.repository = repository;
    }

    public Single<List<AssignmentResponse>> execute(int userId) {
        return repository.getRewardAssignments(userId);
    }
}
