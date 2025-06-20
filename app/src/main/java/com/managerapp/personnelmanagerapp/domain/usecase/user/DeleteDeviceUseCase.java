package com.managerapp.personnelmanagerapp.domain.usecase.user;

import com.managerapp.personnelmanagerapp.data.repository.UserRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Maybe;

public class DeleteDeviceUseCase {
    private final UserRepository userRepository;
    @Inject
    public DeleteDeviceUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Maybe<Boolean> execute() {
        return userRepository.deleteDeviceToken();
    }
}
