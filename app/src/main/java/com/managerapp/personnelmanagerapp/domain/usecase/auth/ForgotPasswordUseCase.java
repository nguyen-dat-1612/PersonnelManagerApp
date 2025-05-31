package com.managerapp.personnelmanagerapp.domain.usecase.auth;

import com.managerapp.personnelmanagerapp.data.repository.AuthRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class ForgotPasswordUseCase {
    private final AuthRepository repository;

    @Inject
    public ForgotPasswordUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public Single<Boolean> excute(String email) {
        return repository.forgotPassword(email);
    }
}
