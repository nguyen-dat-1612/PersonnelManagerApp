package com.managerapp.personnelmanagerapp.domain.usecase.user;

import com.managerapp.personnelmanagerapp.data.repository.UserRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class SaveDevideUseCase {

    private final UserRepository userRepository;

    @Inject
    public SaveDevideUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Single<Boolean> execute(String token) {
        return userRepository.saveDevice(token);
    }
}
