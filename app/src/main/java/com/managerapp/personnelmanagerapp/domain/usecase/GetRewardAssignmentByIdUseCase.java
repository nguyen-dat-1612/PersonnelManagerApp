    package com.managerapp.personnelmanagerapp.domain.usecase;

    import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
    import com.managerapp.personnelmanagerapp.data.repository.RewardAssignmentRepository;
    import com.managerapp.personnelmanagerapp.domain.model.RewardAssignment;

    import javax.inject.Inject;

    import io.reactivex.rxjava3.core.Single;

    public class GetRewardAssignmentByIdUseCase {
        private final RewardAssignmentRepository repository;

        @Inject
        public GetRewardAssignmentByIdUseCase(RewardAssignmentRepository repository) {
            this.repository = repository;
        }

        public Single<AssignmentResponse> execute(int userId, int rewardId) {
            return repository.getRewardAssignmentById(userId, rewardId);
        }
    }
