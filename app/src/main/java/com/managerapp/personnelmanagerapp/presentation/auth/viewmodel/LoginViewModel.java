package com.managerapp.personnelmanagerapp.presentation.auth.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.managerapp.personnelmanagerapp.domain.usecase.auth.LoginUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final LoginUseCase loginUseCase;
    private final MutableLiveData<UiState<String>> uiState = new MutableLiveData<>();
    public LiveData<UiState<String>> getUiState() {
        return uiState;
    }
    private final CompositeDisposable disposables = new CompositeDisposable();
    @Inject
    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public void login(String email, String password) {
        uiState.setValue(UiState.Loading.getInstance());

        disposables.add(loginUseCase.execute(email, password)
                .subscribeOn(Schedulers.io())
                .timeout(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response != null) {
                                uiState.setValue(new UiState.Success("Login Success"));
                            }
                        },
                        throwable -> {
                                uiState.setValue(new UiState.Error(throwable.getMessage()));
                        }
                )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
