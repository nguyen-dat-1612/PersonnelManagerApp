package com.managerapp.personnelmanagerapp.ui.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.LoginUseCase;
import com.managerapp.personnelmanagerapp.ui.state.FeedbackState;
import com.managerapp.personnelmanagerapp.ui.state.LoginState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final LoginUseCase loginUseCase;
    private final MutableLiveData<LoginState> loginState = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();
    private static final String TAG = "LoginViewModel";
    @Inject
    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public LiveData<LoginState> getLoginState() {
        return loginState;
    }

    public void login(String email, String password) {
        loginState.setValue(LoginState.Loading.getInstance());

        disposables.add(loginUseCase.execute(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loginResponse -> {
                            if (loginResponse.getCode() == 200) {
                                loginState.setValue(new LoginState.Success("Đăng nhập thành công"));
                            } else {
                                loginState.setValue(new LoginState.Error("Đăng nhập thất bại!"));
                            }
                        },
                        throwable -> {
                            Log.e(TAG, "Lỗi khi gửi phản hồi: ", throwable);
                            loginState.setValue(new LoginState.Error("Lỗi hệ thống: " + throwable.getMessage()));
                        }
                )
        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();

    }
}
