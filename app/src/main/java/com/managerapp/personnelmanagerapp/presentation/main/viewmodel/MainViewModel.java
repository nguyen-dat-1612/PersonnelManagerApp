package com.managerapp.personnelmanagerapp.presentation.main.viewmodel;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.auth.GetRoleUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.user.GetUserUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.utils.manager.SessionManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
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

    private boolean isDataLoaded = false;

    public boolean isDataLoaded() {
        return isDataLoaded;
    }

    private boolean id;

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public void setDataLoaded(boolean dataLoaded) {
        isDataLoaded = dataLoaded;
    }


    @Inject
    public MainViewModel(GetUserUseCase getUserUseCase, GetRoleUseCase getRoleUseCase, SessionManager sessionManager) {
        this.getUserUseCase = getUserUseCase;
        this.getRoleUseCase = getRoleUseCase;
        this.sessionManager = sessionManager;
    }

    public LiveData<UiState<UserProfileResponse>> getUiState() {
        return uiState;
    }

    public void loadUserAndRole(){
        if (!isDataLoaded) {
            uiState.setValue(UiState.Loading.getInstance());

            disposable.add(
                    Single.zip(
                                    getUserUseCase.execute(),
                                    getRoleUseCase.execute(),
                                    (user, role) -> new Pair<>(user, role)
                            )
                            .subscribeOn(Schedulers.io())
                            .timeout(5, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(pair -> {
                                        // Khi cả 2 use case hoàn tất
                                        UserProfileResponse userProfileResponse = pair.first;
                                        String role = pair.second;
                                        Log.d(TAG, "Role: " + role);
                                        if (userProfileResponse != null) {
                                            uiState.setValue(new UiState.Success(userProfileResponse));
                                            isDataLoaded = true;
                                        } else {
                                            uiState.setValue(new UiState.Error("User not found"));
                                        }
                                    },
                                    throwable -> {
                                        uiState.setValue(new UiState.Error(throwable.getMessage()));
                                    }
                            )
            );
        }
    }

    public String getRole() {
        return sessionManager.getRole();
    }

    @Override
    protected void onCleared() {
        disposable.dispose();  // Thêm phương thức này
        super.onCleared();
    }
}
