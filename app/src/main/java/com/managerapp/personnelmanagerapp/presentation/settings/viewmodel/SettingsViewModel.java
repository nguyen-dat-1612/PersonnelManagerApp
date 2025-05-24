package com.managerapp.personnelmanagerapp.presentation.settings.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.messaging.FirebaseMessaging;
import com.managerapp.personnelmanagerapp.domain.usecase.user.DeleteDeviceUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.user.SaveDevideUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class SettingsViewModel extends ViewModel {
    private final SaveDevideUseCase saveDevideUseCase;
    private final DeleteDeviceUseCase deleteDeviceUseCase;
    private final CompositeDisposable composite = new CompositeDisposable();

    private final MutableLiveData<UiState<Boolean>> uiState = new MutableLiveData<>();

    private final MutableLiveData<UiState<Boolean>> offNotificationUiState = new MutableLiveData<>();

    public LiveData<UiState<Boolean>> getUiState() {
        return uiState;
    }

    @Inject
    public SettingsViewModel(SaveDevideUseCase saveDevideUseCase, DeleteDeviceUseCase deleteDeviceUseCase) {
        this.saveDevideUseCase = saveDevideUseCase;
        this.deleteDeviceUseCase = deleteDeviceUseCase;
    }

    public void saveDeviceToken(String token) {
        uiState.setValue(UiState.Loading.getInstance());

        composite.add(saveDevideUseCase.execute(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS)
                .subscribe(response -> {
                    if (response) {
                        uiState.postValue(new UiState.Success(true));
                    } else {
                        uiState.postValue(new UiState.Error("Failed to save device token"));
                    }
                    }, throwable -> {
                        if (throwable != null) {
                            uiState.postValue(new UiState.Error(throwable.getMessage()));
                        } else {
                            uiState.postValue(new UiState.Error("Unknown error"));
                        }

                }));
    }

    public void fetchFcmToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        saveDeviceToken(token);
                        Log.d("SettingsViewModel", "FCM registration token: " + token);
                    } else {
                        Log.e("SettingsViewModel", "Fetching FCM registration token failed", task.getException());
                        uiState.postValue(new UiState.Error("Fetching FCM registration token failed"));
                    }
                });
    }

    public void offNotificationUiState() {
        offNotificationUiState.setValue(UiState.Loading.getInstance());
        composite.add(deleteDeviceUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response) {
                                offNotificationUiState.postValue(new UiState.Success(true));
                            } else {
                                offNotificationUiState.postValue(new UiState.Error("Failed to remove device token"));
                            }
                        },
                        throwable -> {
                            if (throwable != null) {
                                offNotificationUiState.postValue(new UiState.Error(throwable.getMessage()));
                            } else {
                                offNotificationUiState.postValue(new UiState.Error("Unknown error"));
                            }
                        }
                ));
    }

}
