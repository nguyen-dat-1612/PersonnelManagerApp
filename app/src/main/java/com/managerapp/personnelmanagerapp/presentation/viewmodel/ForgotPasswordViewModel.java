package com.managerapp.personnelmanagerapp.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.ForgotPasswordUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.ResetPasswordUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.VerifyOTPUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.ForgotPasswordState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ForgotPasswordViewModel extends ViewModel {
    private final MutableLiveData<ForgotPasswordState> forgotPasswordState = new MutableLiveData<>();
    private final CompositeDisposable composite = new CompositeDisposable();
    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final VerifyOTPUseCase verifyOTPUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;
    private final String TAG = "ForgotPasswordViewModel";
    private String email;

    @Inject
    public ForgotPasswordViewModel(ForgotPasswordUseCase forgotPasswordUseCase, VerifyOTPUseCase verifyOTPUseCase, ResetPasswordUseCase resetPasswordUseCase) {
        this.forgotPasswordUseCase = forgotPasswordUseCase;
        this.verifyOTPUseCase = verifyOTPUseCase;
        this.resetPasswordUseCase = resetPasswordUseCase;
    }

    public LiveData<ForgotPasswordState> getForgotPasswordState() {
        return forgotPasswordState;
    }


    // Bước 1: Gửi yêu cầu quên mật khẩu
    public void sendForgotPassword(String emailUser) {
        forgotPasswordState.setValue(ForgotPasswordState.Loading.getInstance());
        composite.add(forgotPasswordUseCase.excute(emailUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response == true) {
                        this.email = emailUser;
                        forgotPasswordState.postValue(new ForgotPasswordState.EmailSent(emailUser));
                        Log.d(TAG, "Gửi email quên mật khẩu thành công");
                    } else {
                        Log.d(TAG, "Gửi email không thành công");
                        forgotPasswordState.postValue(new ForgotPasswordState.Error("Gửi email không thành công"));
                    }
                },
                    throwable -> {
                        Log.e(TAG, "Lỗi khi gửi yêu cầu cấp lại mật khẩu: ", throwable);
                        forgotPasswordState.postValue(new ForgotPasswordState.Error("Đã có lỗi xảy ra: " + throwable.getMessage() ));
                    }));
    }
    // Bước 2: Xác minh OTP
    public void verifyOtp(String otp) {
        if (this.email == null) {
            forgotPasswordState.postValue(new ForgotPasswordState.Error("Vui lòng gửi yêu cầu quên mật khẩu trước"));
            return;
        }
        Log.d(TAG,"Mã OTP: " + otp);
        forgotPasswordState.setValue(ForgotPasswordState.Loading.getInstance());
        composite.add(verifyOTPUseCase.excute(this.email,otp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if (response == true) {
                                forgotPasswordState.postValue(new ForgotPasswordState.OtpVerified());
                                Log.d(TAG, "Xác thực OTP thành công");
                            } else {
                                Log.d(TAG, "Mã OTP không chính xác");
                                forgotPasswordState.postValue(new ForgotPasswordState.Error("Mã OTP không chính xác"));
                            }
                        },
                        throwable -> {
                            Log.e(TAG, "Lỗi khi gửi xác nhận OTP: ", throwable);
                            forgotPasswordState.postValue(new ForgotPasswordState.Error("Đã có lỗi xảy ra: " + throwable.getMessage() ));
                        }));
    }
    // Bước 3: Đặt lại mật khẩu
    public void resetPassword(String newPassword) {
        if (this.email == null) {
            forgotPasswordState.postValue(new ForgotPasswordState.Error("Vui lòng xác thực OTP trước"));
            return;
        }
        forgotPasswordState.setValue(ForgotPasswordState.Loading.getInstance());
        composite.add(resetPasswordUseCase.excute(newPassword, this.email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if (response == true) {
                                forgotPasswordState.postValue(new ForgotPasswordState.PasswordResetSuccess());
                                Log.d(TAG, "Thay đổi mật khẩu thành công");
                            } else {
                                Log.d(TAG, "Mật khẩu không được chấp nhận");
                                forgotPasswordState.postValue(new ForgotPasswordState.Error("Mật khẩu không được chấp nhận"));
                            }
                        },
                        throwable -> {
                            Log.e(TAG, "Lỗi khi gửi xác nhận OTP: ", throwable);
                            forgotPasswordState.postValue(new ForgotPasswordState.Error("Đã có lỗi xảy ra: " + throwable.getMessage() ));
                        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (composite != null) composite.dispose();
    }
}
