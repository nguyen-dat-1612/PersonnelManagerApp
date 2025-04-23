package com.managerapp.personnelmanagerapp.presentation.leaveapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.leaveapp.ConfirmApplicationUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.leaveapp.GetApplicationIsPendingUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LeaveRequestViewModel extends ViewModel {
    private final String TAG = "LeaveRequestViewModel";

    private final GetApplicationIsPendingUseCase getApplicationIsPendingUseCase;
    private final ConfirmApplicationUseCase confirmApplicationUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<UiState<List<LeaveApplicationResponse>>> uiStateLoad = new MutableLiveData<>();

    private final MutableLiveData<UiState<LeaveApplicationResponse>> uiStateConfirm = new MutableLiveData<>();

    @Inject
    public LeaveRequestViewModel(GetApplicationIsPendingUseCase getApplicationIsPendingUseCase, ConfirmApplicationUseCase confirmApplicationUseCase) {
        this.getApplicationIsPendingUseCase = getApplicationIsPendingUseCase;
        this.confirmApplicationUseCase = confirmApplicationUseCase;
    }

    public LiveData<UiState<List<LeaveApplicationResponse>>> getUiStateLoad() {
        return uiStateLoad;
    }

    public LiveData<UiState<LeaveApplicationResponse>> getUiStateConfirm() {
        return uiStateConfirm;
    }

    public void getApplicationIsPending() {
        Log.d(TAG, "getApplicationIsPending");
        uiStateLoad.setValue(UiState.Loading.getInstance());
        disposables.add(getApplicationIsPendingUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        leaveApplicationResponses -> {
                            Log.d(TAG, "Lấy dữ liệu thành công");
                            uiStateLoad.postValue(new UiState.Success<>(leaveApplicationResponses));
                        },
                        throwable -> {
                            Log.e(TAG, throwable.getMessage());
                            uiStateLoad.postValue(new UiState.Error<>(throwable.getMessage()));
                        }
                )
        );
    }

    public void confirmApplication(long applicationId, String formStatusEnum) {
        uiStateConfirm.setValue(UiState.Loading.getInstance());
        disposables.add(confirmApplicationUseCase.execute(applicationId, formStatusEnum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response.getCode() == 200) {
                                uiStateConfirm.postValue(new UiState.Success<>(response.getData()));
                            } else {
                                uiStateConfirm.postValue(new UiState.Error<>(response.getMessage()));
                            }
                        },
                        throwable -> {
                            uiStateConfirm.postValue(new UiState.Error<>(throwable.getMessage()));
                        }
                )
        );
    }
}
