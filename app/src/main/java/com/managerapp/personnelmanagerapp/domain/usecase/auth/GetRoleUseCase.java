package com.managerapp.personnelmanagerapp.domain.usecase.auth;

import com.managerapp.personnelmanagerapp.data.repository.AuthRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetRoleUseCase {
    private final AuthRepository authRepository;

    @Inject
    public GetRoleUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Single<String> execute() {
        return authRepository.getRole();
    }
}
