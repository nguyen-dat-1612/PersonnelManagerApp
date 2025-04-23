package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.utils.LocalDataManager;
import com.managerapp.personnelmanagerapp.data.remote.api.UserApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.ChangePasswordRequest;
import com.managerapp.personnelmanagerapp.domain.model.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class UserRepository {

    private final UserApiService apiService;
    private final String TAG = "UserRepository";
    private static final String SUCCESS_MESSAGE = "Change successful";
    private static final String TRUE_STRING = "true";
    private final LocalDataManager localDataManager;
    @Inject
    public UserRepository(UserApiService apiService, LocalDataManager localDataManager) {
        this.apiService = apiService;
        this.localDataManager = localDataManager;
    }

    public Single<UserProfileResponse> getUser() {
        return apiService.getUser()
                .flatMap( response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        UserProfileResponse user = response.body().getData();
                        localDataManager.saveUserId(String.valueOf(user.getId()));
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
    public Single<Boolean> changePasswordUser(String oldPass, String newPass) {
        Log.d(TAG, "Bắt đầu changePasswordUser() | oldPass: [****], newPass: [****]");

        // Log userId (ẩn mật khẩu vì lý do bảo mật)
        long userId = Long.parseLong(localDataManager.getUserId());
        Log.d(TAG, "UserId: " + userId);

        // Tạo request
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(userId, oldPass, newPass);
        Log.d(TAG, "Tạo ChangePasswordRequest: " + changePasswordRequest.toString().replace(oldPass, "***").replace(newPass, "***"));

        return apiService.changePassword(changePasswordRequest)
                .doOnSubscribe(disposable ->
                        Log.d(TAG, "Gọi API changePassword...")
                )
                .map(response -> {
                    Log.d(TAG, "Nhận phản hồi từ API | Code: " + response.code());

                    if (response.isSuccessful() && response.body() != null) {
                        String serverMessage = response.body().getMessage();
                        boolean isSuccess = SUCCESS_MESSAGE.equalsIgnoreCase(serverMessage);

                        Log.d(TAG, "Phản hồi từ server: " + serverMessage);
                        Log.d(TAG, "isSuccess: " + isSuccess);

                        if (isSuccess) {
                            Log.i(TAG, "✅ Đổi mật khẩu THÀNH CÔNG cho userId: " + userId);
                            return true;
                        } else {
                            Log.e(TAG, "❌ Đổi mật khẩu THẤT BẠI: " + serverMessage);
                            throw new Exception(serverMessage != null ? serverMessage : "Lỗi không xác định từ server");
                        }
                    }

                    // Xử lý response lỗi
                    String errorMessage = "Lỗi server (code: " + response.code() + ")";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                            Log.e(TAG, "Chi tiết lỗi từ errorBody: " + errorMessage);
                        } catch (Exception e) {
                            Log.e(TAG, "Không thể đọc errorBody", e);
                        }
                    }
                    throw new Exception(errorMessage);
                })
                .doOnError(error ->
                        Log.e(TAG, "🔥 Lỗi trong quá trình changePasswordUser: " + error.getMessage(), error)
                )
                .doOnSuccess(success ->
                        Log.d(TAG, "Kết thúc changePasswordUser | Kết quả: " + success)
                );
    }

    public Single<List<WorkLogResponse>> getWorkLog(long userId) {
        return apiService.getWorkLog(userId)
                .flatMap( response -> {
                    Log.d(TAG, "Mã phản hồi HTTP: " + response.body());
                    if (response.isSuccessful() && response.body() != null && response.body().getCode() == 200) {
                        Log.d(TAG, "Lấy dữ liệu thành công: " + response.body().getData().toString());
                        return Single.just(response.body().getData());
                    } else {
                        String errorMessage = "Lấy quá trình công tác thất bại: " + response.body().getMessage();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                                Log.e(TAG, "Chi tiết lỗi từ errorBody: " + errorMessage);
                            } catch (Exception e) {
                                Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                            }
                        }
                        Log.d(TAG, "Lấy dữ liệu thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                })
                .doOnError(error -> Log.e(TAG, "Lỗi trong quá trình lấy dữ liệu: " + error.getMessage(), error));
    }
}
