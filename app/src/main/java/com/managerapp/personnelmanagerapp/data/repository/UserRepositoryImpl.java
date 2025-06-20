package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.mapper.UserSummaryMapper;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.api.UserApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.ChangePasswordRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.domain.model.UserSummary;
import com.managerapp.personnelmanagerapp.domain.repository.UserRepository;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

//@Singleton
public class UserRepositoryImpl implements UserRepository {
    private final UserApiService apiService;
    private final LocalDataManager localDataManager;
    private final Map<String, List<UserSummary>> cache = new HashMap<>();
    private final RxResultHandler rxResultHandler;

    @Inject
    public UserRepositoryImpl(UserApiService apiService, LocalDataManager localDataManager, RxResultHandler rxResultHandler) {
        this.apiService = apiService;
        this.localDataManager = localDataManager;
        this.rxResultHandler = rxResultHandler;
    }

    public Single<UserProfileResponse> getUser() {
        return rxResultHandler.handleSingle(apiService.getUser())
                .doOnSuccess(userProfileResponse -> {
                    if (userProfileResponse != null) {
                        localDataManager.saveUserId(userProfileResponse.getId());
                    }
                });
    }

    public Maybe<Boolean> changePasswordUser(String oldPass, String newPass) {
        return localDataManager.getUserIdAsync()
                .toMaybe()
                .flatMap( userId -> apiService.changePassword(new ChangePasswordRequest(userId, oldPass, newPass)))
                .map(response -> response.getCode() == 200)
                .doOnError(throwable -> Maybe.error(throwable));
    }

    public Single<List<WorkLogResponse>> getWorkLog() {
        return localDataManager.getUserIdAsync()
                .flatMap( userId -> {
                    return rxResultHandler.handleSingle(apiService.getWorkLog(userId));
                });
    }

    public Single<Boolean> saveDevice(String token) {
        return localDataManager.getUserIdAsync()
                .flatMap( userId -> {
                    return rxResultHandler.handleSingle(apiService.saveDevice(userId, token));
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
                    List<UserSummary> list = UserSummaryMapper.toUserSummaryList(response.getData());
                    cache.put(query, list);
                    return list;
                })
                .subscribeOn(Schedulers.io());
    }
}
