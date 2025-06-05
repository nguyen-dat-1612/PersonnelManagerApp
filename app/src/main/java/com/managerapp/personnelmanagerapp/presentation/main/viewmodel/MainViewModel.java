package com.managerapp.personnelmanagerapp.presentation.main.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.auth.GetRoleUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.user.GetUserUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.manager.SessionManager;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final String TAG = "MainViewModel";
    private final GetUserUseCase getUserUseCase;
    private final GetRoleUseCase getRoleUseCase;
    private final SessionManager sessionManager;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<UiState<UserProfileResponse>> uiState = new MutableLiveData<>();
    private final MutableLiveData<UiState<String>> roleUiState = new MutableLiveData<>();

    private boolean isUserLoaded = false;
    private boolean isRoleLoaded = false;

    @Inject
    public MainViewModel(GetUserUseCase getUserUseCase, GetRoleUseCase getRoleUseCase, SessionManager sessionManager) {
        this.getUserUseCase = getUserUseCase;
        this.getRoleUseCase = getRoleUseCase;
        this.sessionManager = sessionManager;
    }

    public LiveData<UiState<UserProfileResponse>> getUiState() {
        return uiState;
    }

    public LiveData<UiState<String>> getRoleUiState() {
        return roleUiState;
    }

    public void loadUser() {
        if (!isUserLoaded) {
            uiState.setValue(UiState.Loading.getInstance());

            disposable.add(
                    getUserUseCase.execute()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    user -> {
                                        isUserLoaded = true;
                                        uiState.setValue(new UiState.Success(user));
                                    },
                                    throwable -> {
                                        Log.e(TAG, "loadUser failed: " + throwable.getMessage());
                                        uiState.setValue(new UiState.Error("Failed to load user: " + throwable.getMessage()));
                                    }
                            )
            );
        }
    }

    public void loadRole() {
        if (!isRoleLoaded) {
            roleUiState.setValue(UiState.Loading.getInstance());

            disposable.add(
                    getRoleUseCase.execute()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    role -> {
                                        isRoleLoaded = true;
                                        roleUiState.setValue(new UiState.Success(role));
                                    },
                                    throwable -> {
                                        Log.e(TAG, "loadRole failed: " + throwable.getMessage());
                                        roleUiState.setValue(new UiState.Error("Failed to load role: " + throwable.getMessage()));
                                    }
                            )
            );
        }
    }

    public void loadUserAndRole() {
        loadUser();
        loadRole();
    }

    public boolean isDataLoaded() {
        return isUserLoaded || isRoleLoaded;
    }

    public void logout() {
        sessionManager.clearSession();
        isUserLoaded = false;
        isRoleLoaded = false;
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
