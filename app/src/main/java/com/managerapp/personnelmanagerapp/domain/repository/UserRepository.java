package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.request.ChangePasswordRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserSummaryResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.domain.model.UserSummary;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface UserRepository {
    Single<UserProfileResponse> getUser();
    Maybe<Boolean> changePasswordUser(String oldPass, String newPass);
    Single<List<WorkLogResponse>> getWorkLog();
    Single<Boolean> saveDevice(String token);
    Maybe<Boolean> deleteDeviceToken();
    Observable<List<UserSummary>> searchUser(String query);
}
