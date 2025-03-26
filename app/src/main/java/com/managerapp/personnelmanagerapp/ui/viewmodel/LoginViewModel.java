package com.managerapp.personnelmanagerapp.ui.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.LoginUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private final LoginUseCase loginUseCase;
    private final MutableLiveData<LoginResponse> loginLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public MutableLiveData<LoginResponse> getUserLiveData() {
        return loginLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
    public void login(String email, String password) {
        Disposable disposable = loginUseCase.execute(email, password)
                .subscribe(
                        response -> {
                            loginLiveData.postValue(response);
                        },
                        throwable -> {
                            errorLiveData.postValue(throwable.getMessage());
                        }
                );
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

    }
}
