package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.api.UserApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.ChangePasswordRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserSummaryResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.domain.model.UserSummary;
import com.managerapp.personnelmanagerapp.domain.repository.UserRepository;
import com.managerapp.personnelmanagerapp.utils.manager.LocalDataManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class UserRepositoryImpl implements UserRepository {
    private final UserApiService apiService;
    private final LocalDataManager localDataManager;
    private final Map<String, List<UserSummary>> cache = new HashMap<>();

    @Inject
    public UserRepositoryImpl(UserApiService apiService, LocalDataManager localDataManager) {
        this.apiService = apiService;
        this.localDataManager = localDataManager;
    }

    public Single<UserProfileResponse> getUser() {
        return RxResultHandler.handle(apiService.getUser())
                .doOnSuccess(userProfileResponse -> {
                    localDataManager.saveUserInfo(userProfileResponse);
                    if (userProfileResponse != null) {
                        localDataManager.saveUserId(userProfileResponse.getId());
                    }
                });
    }

    public Single<Boolean> changePasswordUser(String oldPass, String newPass) {
        return localDataManager.getUserIdAsync()
                .flatMap( userId -> {
                    ChangePasswordRequest request = new ChangePasswordRequest(userId, oldPass, newPass);
                    return RxResultHandler.handle(apiService.changePassword(request));
                });
    }

    public Single<List<WorkLogResponse>> getWorkLog() {
        return localDataManager.getUserIdAsync()
                .flatMap( userId -> {
                    return RxResultHandler.handle(apiService.getWorkLog(userId));
                });
    }

    public Single<Boolean> saveDevice(String token) {
        return localDataManager.getUserIdAsync()
                .flatMap( userId -> {
                    return RxResultHandler.handle(apiService.saveDevice(userId, token));
                });

    }

    public Maybe<Boolean> deleteDeviceToken() {
        return localDataManager.getUserIdAsync()
                .flatMapMaybe(userId ->
                        apiService.removeDevice(userId)
                                .filter(response -> response.getCode() == 200)
                                .map(response -> true)
                );
    }

    public Observable<List<UserSummary>> searchUser(String query) {
        if (cache.containsKey(query)) {
            return Observable.just(cache.get(query));
        }

        return apiService.searchUser(query)
                .map(response -> {
                    List<UserSummary> list = new ArrayList<>();
                    for (UserSummaryResponse dto : response.getData()) {
                        list.add(dto.toUserSummary());
                    }
                    cache.put(query, list);
                    return list;
                })
                .subscribeOn(Schedulers.io());
    }
}
