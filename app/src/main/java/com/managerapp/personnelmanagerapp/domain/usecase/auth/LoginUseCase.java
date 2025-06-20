package com.managerapp.personnelmanagerapp.domain.usecase.auth;

import com.managerapp.personnelmanagerapp.data.repository.AuthRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class LoginUseCase {
    private final AuthRepository authRepository;
    @Inject
    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Single<String> execute(String email, String password) {
        return authRepository.login(email, password);
    }
}
