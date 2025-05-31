package com.managerapp.personnelmanagerapp.domain.usecase.auth;

import com.managerapp.personnelmanagerapp.data.repository.AuthRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class ResetPasswordUseCase {
    private final AuthRepository authRepository;

    @Inject
    public ResetPasswordUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Single<String> excute(String newPassword, String email) {
        return authRepository.resetPassword(newPassword, email);
    }

}
