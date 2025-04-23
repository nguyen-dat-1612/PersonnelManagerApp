package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.utils.SecureTokenManager;
import com.managerapp.personnelmanagerapp.data.remote.api.AuthApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.utils.SessionManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class AuthRepository {
    private static final String TAG = "AuthRepository";
    private static final String SUCCESS_MESSAGE = "Successful";
    private static final String TRUE_STRING = "true";

    private final AuthApiService authApiService;
    private final SecureTokenManager secureTokenManager;
    private final SessionManager sessionManager;

    @Inject
    public AuthRepository(AuthApiService authApiService, SecureTokenManager secureTokenManager, SessionManager sessionManager) {
        this.authApiService = authApiService;
        this.secureTokenManager = secureTokenManager;
        this.sessionManager = sessionManager;
    }

    public Single<BaseResponse<String>> login(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        return authApiService.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Đăng nhập thành công");
                        Log.d(TAG, response.body().getData());
                        secureTokenManager.saveAccessToken(response.body().getData());
//                        secureTokenManager.saveRefreshToken(response.refreshToken);
//                        secureTokenManager.setTokenExpiry(response.expiryTime);
                        return Single.just(response.body());
                    } else {
                        String errorMessage = "Đăng nhập thất bại với mã lỗi: " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {
                                Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                            }
                        }
                        Log.d(TAG, "Đăng nhập thất bại: " + email + "." + password + ": " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }

    public Single<Boolean> forgotPassword(String email) {
        return authApiService.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .map(response -> {
                    if (response == null) {
                        Log.d(TAG, "Response null");
                    }
                    Log.d(TAG, response.body().toString());
                    if (response.isSuccessful() && response.body() != null) {
                        return SUCCESS_MESSAGE.equalsIgnoreCase(response.body().getMessage());
                    }
                    return false;
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "Lỗi khi gửi yêu cầu lấy lại mật khẩu: ", throwable);
                })
                .onErrorReturnItem(false);
    }

    public Single<Boolean> verifyOTP(String email, String otp) {
        return authApiService.verifyOTP(email,otp)
                .subscribeOn(Schedulers.io())
                .map(response -> {
                    if (response == null) {
                        Log.e(TAG, "Response từ API là null");
                        return false;
                    }
                    if (!response.isSuccessful()) {
                        Log.e(TAG, "Lỗi API: Mã lỗi " + response.code());
                        return false;
                    }
                    BaseResponse<Boolean> body = response.body();
                    if (body == null) {
                        Log.e(TAG, "Response body là null");
                        return false;
                    }

                    boolean result = body.getData();  // Đảm bảo body != null trước khi gọi getData()
                    Log.d(TAG, "In ra thông tin của response: " + result);
                    return result;
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "Lỗi khi nhập OTP: ", throwable);
                })
                .onErrorReturnItem(false);
    }

    public Single<Boolean> resetPassword(String newPass, String email) {
        return authApiService.resetPassword(newPass, email)
                .subscribeOn(Schedulers.io())
                .map(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        // Lưu token nhận được từ API reset password
                        String newToken = response.body().getData();
                        secureTokenManager.saveAccessToken(newToken);
                        return true;
                    }
                    return false;
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "Lỗi khi reset password: ", throwable);
                })
                .onErrorReturnItem(false);
    }

    public Single<BaseResponse<String>> getRole() {
        return authApiService.getRole(secureTokenManager.getAccessToken())
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Lấy role thành công: " + response.body().getData());
                        sessionManager.saveRole(response.body().getData());
                        return Single.just(response.body());
                    } else {
                        String errorMessage = "Lỗi khi lấy role: Mã lỗi " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {
                                Log.e(TAG, "Không đọc được errorBody", e);
                            }
                        }
                        return Single.error(new Exception(errorMessage));
                    }
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "Lỗi khi lấy role: ", throwable);
                });
    }


//    public Single<TokenRefreshResponse> refreshToken() {
//        String refreshToken = secureTokenManager.getRefreshToken();
//        if (refreshToken == null) {
//            return Single.error(new Exception("No refresh token"));
//        }
//
//        TokenRefreshRequest request = new TokenRefreshRequest(refreshToken);
//        return authApiService.refreshToken(request)
//                .doOnSuccess(response -> {
//                    secureTokenManager.saveAccessToken(response.getAccessToken());
//                    secureTokenManager.setTokenExpiry(response.getExpiresIn());
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
}
