package com.managerapp.personnelmanagerapp.domain.usecase.user;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.data.repository.UserRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.scopes.ViewModelScoped;
import io.reactivex.rxjava3.core.Single;


@ViewModelScoped
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
