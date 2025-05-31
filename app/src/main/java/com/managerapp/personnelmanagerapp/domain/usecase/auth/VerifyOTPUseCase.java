package com.managerapp.personnelmanagerapp.domain.usecase.auth;

import com.managerapp.personnelmanagerapp.data.repository.AuthRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class VerifyOTPUseCase {
    private final AuthRepository authRepository;

    @Inject
    public VerifyOTPUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Single<Boolean> excute(String email, String otp) {
        return authRepository.verifyOTP(email, otp);
    }
}
