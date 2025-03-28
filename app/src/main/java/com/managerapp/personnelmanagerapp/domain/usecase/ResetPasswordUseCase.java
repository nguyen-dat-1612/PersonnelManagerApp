package com.managerapp.personnelmanagerapp.domain.usecase;

import com.managerapp.personnelmanagerapp.data.repository.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class ResetPasswordUseCase {
    private final AuthRepository authRepository;

    @Inject
    public ResetPasswordUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Single<Boolean> excute(String newPassword, String email) {
        return authRepository.resetPassword(newPassword, email);
    }

}
