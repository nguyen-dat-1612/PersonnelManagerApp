package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.google.gson.Gson;
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
    private static final String SUCCESS_MESSAGE = "Successful";
    private static final String TRUE_STRING = "true";

    @Inject
    public UserRepository(UserApiService apiService) {
        this.apiService = apiService;
    }

    // Lấy thông tin người đăng nhập
    public Single<User> getUser() {
        return apiService.getUser()
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

    // Đổi mật khẩu
    public Single<Boolean> changePasswordUser(int userId, String oldPass, String newPass) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(userId, oldPass, newPass);

        return apiService.changePassword(changePasswordRequest)
                .map(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        boolean isSuccess = SUCCESS_MESSAGE.equalsIgnoreCase(response.body().getMessage());
                        if (isSuccess) {
                            Log.d(TAG, "Đổi mật khẩu thành công cho userId: " + userId);
                            return true;
                        }
                        throw new Exception(response.body().getMessage() != null ?
                                response.body().getMessage() : "Lỗi không xác định từ server");
                    }
                    String errorMessage = "Lỗi server (code: " + response.code() + ")";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = new Gson().toJson(response.errorBody().string());
                        } catch (Exception e) {
                            Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                        }
                    }
                    throw new Exception(errorMessage);
                });
    }

}
