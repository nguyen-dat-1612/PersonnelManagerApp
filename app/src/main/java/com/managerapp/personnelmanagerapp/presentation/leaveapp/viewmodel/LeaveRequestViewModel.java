package com.managerapp.personnelmanagerapp.presentation.leaveapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.department.GetDepartmentByUserIdUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.leaveapp.ConfirmApplicationUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.leaveapp.GetApplicationIsPendingUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private final GetDepartmentByUserIdUseCase getDepartmentByUserIdUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<UiState<List<LeaveApplicationResponse>>> uiStateLoad = new MutableLiveData<>();

    private final MutableLiveData<UiState<LeaveApplicationResponse>> uiStateConfirm = new MutableLiveData<>();

    @Inject
    public LeaveRequestViewModel(GetApplicationIsPendingUseCase getApplicationIsPendingUseCase, ConfirmApplicationUseCase confirmApplicationUseCase, GetDepartmentByUserIdUseCase getDepartmentByUserIdUseCase) {
        this.getApplicationIsPendingUseCase = getApplicationIsPendingUseCase;
        this.confirmApplicationUseCase = confirmApplicationUseCase;
        this.getDepartmentByUserIdUseCase = getDepartmentByUserIdUseCase;
    }

    public LiveData<UiState<List<LeaveApplicationResponse>>> getUiStateLoad() {
        return uiStateLoad;
    }

    public LiveData<UiState<LeaveApplicationResponse>> getUiStateConfirm() {
        return uiStateConfirm;
    }

    public void getApplicationIsPending(String formStatusEnum) {
        uiStateLoad.setValue(UiState.Loading.getInstance());

        disposables.add(getDepartmentByUserIdUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .flatMap(department -> { return getApplicationIsPendingUseCase.execute(formStatusEnum, department.getId());
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                leaveApplicationResponses -> {
                                    Log.d(TAG, "getApplicationIsPending: " + leaveApplicationResponses.size());
                                    uiStateLoad.postValue(new UiState.Success<>(leaveApplicationResponses));
                                },
                                throwable -> {
                                    Log.e(TAG, "getApplicationIsPending: " + throwable.getMessage());
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
                .timeout(5, TimeUnit.SECONDS)
                .subscribe(
                        response -> {
                            if (response != null) {
                                uiStateConfirm.postValue(new UiState.Success<>(response));
                                removeConfirmedApplication(applicationId);
                            } else {
                                uiStateConfirm.postValue(new UiState.Error<>("Không thể phê duyệt yêu cầu"));
                            }
                        },
                        throwable -> {
                            uiStateConfirm.postValue(new UiState.Error<>(throwable.getMessage()));
                        }
                )
        );
    }
    private void removeConfirmedApplication(long id) {
        UiState<List<LeaveApplicationResponse>> currentState = uiStateLoad.getValue();
        if (currentState instanceof UiState.Success) {
            List<LeaveApplicationResponse> currentList = ((UiState.Success<List<LeaveApplicationResponse>>) currentState).getData();
            List<LeaveApplicationResponse> updatedList = new ArrayList<>();
            for (LeaveApplicationResponse item : currentList) {
                if (item.getId() != id) {
                    updatedList.add(item);
                }
            }
            uiStateLoad.postValue(new UiState.Success<>(updatedList));
        }
    }
}
