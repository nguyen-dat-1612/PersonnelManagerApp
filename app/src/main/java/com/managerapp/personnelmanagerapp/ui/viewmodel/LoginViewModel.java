package com.managerapp.personnelmanagerapp.ui.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.local.UserPreferences;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.data.repository.AuthRepository;
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
    private final UserPreferences userPreferences;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public LoginViewModel(LoginUseCase loginUseCase, UserPreferences userPreferences) {
        this.loginUseCase = loginUseCase;
        this.userPreferences = userPreferences;
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
                            userPreferences.saveTokens(response.getData().getAccessToken(), response.getData().getRefreshToken());
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
