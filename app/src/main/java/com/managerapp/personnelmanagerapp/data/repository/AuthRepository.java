package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.managerapp.personnelmanagerapp.data.remote.api.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.exceptions.ApiException;
import com.managerapp.personnelmanagerapp.data.remote.api.AuthApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.utils.manager.SecureTokenManager;
import com.managerapp.personnelmanagerapp.utils.manager.SessionManager;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class AuthRepository {
    private static final String TAG = "AuthRepository";

    private final AuthApiService authApiService;
    private final SecureTokenManager secureTokenManager;
    private final SessionManager sessionManager;
    private final Gson gson = new Gson();

    @Inject
    public AuthRepository(AuthApiService authApiService, SecureTokenManager secureTokenManager, SessionManager sessionManager) {
        this.authApiService = authApiService;
        this.secureTokenManager = secureTokenManager;
        this.sessionManager = sessionManager;
    }

    public Single<String> login(String email, String password) {
        return RxResultHandler.handle(authApiService.login(new LoginRequest(email, password))
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
        return RxResultHandler.handle(authApiService.verifyOTP(email, otp));
    }

    public Single<String> resetPassword(String newPass, String email) {
        return RxResultHandler.handle(authApiService.resetPassword(newPass, email)
                .doOnSuccess(
                        response -> secureTokenManager.saveAccessToken(response.getData())
                ));
    }

    public Single<String> getRole() {
        return RxResultHandler.handle(authApiService.getRole(secureTokenManager.getAccessToken()))
                .doOnSuccess(
                        role -> sessionManager.saveRole(role)
                );
    }
}
