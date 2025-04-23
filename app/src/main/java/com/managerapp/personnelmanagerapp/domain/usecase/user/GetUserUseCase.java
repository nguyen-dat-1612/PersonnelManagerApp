package com.managerapp.personnelmanagerapp.domain.usecase.user;

import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.data.repository.UserRepository;
import com.managerapp.personnelmanagerapp.domain.model.User;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;


public class GetUserUseCase {
    private final UserRepository userRepository;

    @Inject
    public GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Single<UserProfileResponse> execute() {
        return userRepository.getUser();
    }

}
