package com.managerapp.personnelmanagerapp.domain.usecase;

import com.managerapp.personnelmanagerapp.data.repository.UserRepository;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.domain.model.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;


public class GetUserUseCase {
    private final UserRepository userRepository;

    @Inject
    public GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Single<User> execute(int userId) {
        return userRepository.getUser(userId);
    }

}
