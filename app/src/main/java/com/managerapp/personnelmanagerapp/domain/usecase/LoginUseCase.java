package com.managerapp.personnelmanagerapp.domain.usecase;

import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.data.repository.AuthRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginUseCase {
    private final AuthRepository authRepository;

    @Inject
    public LoginUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Single<LoginResponse> execute(String email, String password) {
        return authRepository.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
