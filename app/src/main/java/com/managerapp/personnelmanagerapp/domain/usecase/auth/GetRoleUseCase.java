package com.managerapp.personnelmanagerapp.domain.usecase.auth;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.repository.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetRoleUseCase {
    private final AuthRepository authRepository;

    @Inject
    public GetRoleUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Single<BaseResponse<String>> execute() {
        return authRepository.getRole();
    }
}
