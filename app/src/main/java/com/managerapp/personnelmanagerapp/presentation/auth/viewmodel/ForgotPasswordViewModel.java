package com.managerapp.personnelmanagerapp.presentation.auth.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.auth.ForgotPasswordUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.auth.ResetPasswordUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.auth.VerifyOTPUseCase;
import com.managerapp.personnelmanagerapp.presentation.auth.state.ForgotPasswordState;

import java.util.concurrent.TimeUnit;

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
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response) {
                        this.email = emailUser;
                        forgotPasswordState.postValue(new ForgotPasswordState.EmailSent(emailUser));
                    }
                },
                    throwable -> {
                        forgotPasswordState.postValue(new ForgotPasswordState.Error(throwable.getMessage()));
                    }));
    }

    public void verifyOtp(String otp) {
        forgotPasswordState.setValue(ForgotPasswordState.Loading.getInstance());
        composite.add(verifyOTPUseCase.excute(this.email,otp)
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if (response) {
                                forgotPasswordState.postValue(new ForgotPasswordState.OtpVerified());
                            }
                        },
                        throwable -> {
                            forgotPasswordState.postValue(new ForgotPasswordState.Error(throwable.getMessage() ));
                        }));
    }

    // Bước 3: Đặt lại mật khẩu
    public void resetPassword(String newPassword) {
        forgotPasswordState.setValue(ForgotPasswordState.Loading.getInstance());
        composite.add(resetPasswordUseCase.excute(newPassword, this.email)
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if (response != null) {
                                forgotPasswordState.postValue(new ForgotPasswordState.PasswordResetSuccess());
                            }
                        },
                        throwable -> {
                            forgotPasswordState.postValue(new ForgotPasswordState.Error(throwable.getMessage() ));
                        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (composite != null) composite.dispose();
    }
}
