package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.api.UserApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.ChangePasswordRequest;
import com.managerapp.personnelmanagerapp.domain.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class UserRepository {
    private final UserApiService apiService;
    private final String TAG = "UserRepository";

    @Inject
    public UserRepository(UserApiService apiService) {
        this.apiService = apiService;
    }

    public Single<User> getUser(int userId) {
        return apiService.getUser(userId)
                .flatMap( response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Lấy dữ liệu thông tin người dùng thành công");
                        return Single.just(response.body().getData());
                    } else {
                        String errorMessage = "Lấy dữ liệu người dùng thất bại: " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {
                                Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                            }
                        }
                        Log.d(TAG, "Lấy dữ liệu thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                }
        );
    }

    public Single<String> changePasswordUser(int userId, String currentPassword, String newPassword) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(currentPassword, newPassword);
        return apiService.changePassword(userId, changePasswordRequest)
                .flatMap( response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Đổi mật khẩu thành công");
                        return Single.just(response.body().getStatus());
                    } else {
                        String errorMessage  = "Đổi mật khẩu người dùng thất bại: " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {
                                Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                            }
                        }
                        Log.d(TAG, "Đổi mật khẩu thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }
}
