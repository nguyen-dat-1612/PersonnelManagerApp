package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.api.AuthApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
import com.managerapp.personnelmanagerapp.domain.repository.AuthRepository;
import com.managerapp.personnelmanagerapp.manager.SecureTokenManager;
import com.managerapp.personnelmanagerapp.manager.SessionManager;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthRepositoryImpl implements AuthRepository {
    private final RxResultHandler rxResultHandler;
    private final AuthApiService authApiService;
    private final SecureTokenManager secureTokenManager;
    private final SessionManager sessionManager;

    @Inject
    public AuthRepositoryImpl(RxResultHandler rxResultHandler, AuthApiService authApiService, SecureTokenManager secureTokenManager, SessionManager sessionManager) {
        this.rxResultHandler = rxResultHandler;
        this.authApiService = authApiService;
        this.secureTokenManager = secureTokenManager;
        this.sessionManager = sessionManager;
    }


    public Single<String> login(String email, String password) {
        return rxResultHandler.handleSingle(authApiService.login(new LoginRequest(email, password))
                .doOnSuccess(response ->
                        secureTokenManager.saveAccessToken(response.getData()))
        );
    }
    public Single<Boolean> forgotPassword(String email) {
        return authApiService.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .map(response -> response.getCode() == 200)
                .onErrorReturnItem(false);
    }

    public Single<Boolean> verifyOTP(String email, String otp) {
        return rxResultHandler.handleSingle(authApiService.verifyOTP(email, otp));
    }

    public Single<String> resetPassword(String newPass, String email) {
        return rxResultHandler.handleSingle(authApiService.resetPassword(newPass, email)
                .doOnSuccess(
                        response -> secureTokenManager.saveAccessToken(response.getData())
                ));
    }

    public Single<String> getRole() {
        return rxResultHandler.handleSingle(authApiService.getRole(secureTokenManager.getAccessToken()))
                .doOnSuccess(
                        role -> sessionManager.saveRole(role)
                );
    }
}
