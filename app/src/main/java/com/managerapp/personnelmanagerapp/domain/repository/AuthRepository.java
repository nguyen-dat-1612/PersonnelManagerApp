package com.managerapp.personnelmanagerapp.domain.repository;

import io.reactivex.rxjava3.core.Single;

public interface AuthRepository {

    Single<String> login(String email, String password);

    Single<Boolean> forgotPassword(String email);

    Single<Boolean> verifyOTP(String email, String otp);

    Single<String> resetPassword(String newPass, String email);

    Single<String> getRole();
}
