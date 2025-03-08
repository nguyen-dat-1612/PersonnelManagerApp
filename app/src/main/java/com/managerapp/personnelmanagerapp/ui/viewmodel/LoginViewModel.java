package com.managerapp.personnelmanagerapp.ui.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.local.UserPreferences;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.data.repository.AuthRepository;
import com.managerapp.personnelmanagerapp.domain.usecase.LoginUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final LoginUseCase loginUseCase;
   private final MutableLiveData<LoginResponse> loginLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final UserPreferences userPreferences;

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
    public Boolean login(String email, String password) {
        loginUseCase.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(LoginResponse  response) {
                loginLiveData.postValue(response);

                // Lưu token vào DataStore
                // Chạy bất đồng bộ để lưu token
                userPreferences.saveTokens(response.getData().getAccessToken(), response.getData().getRefreshToken());
            }

            @Override
            public void onFailure(String error) {
                errorLiveData.postValue(error);
            }
        });
        return true;
    }

}
