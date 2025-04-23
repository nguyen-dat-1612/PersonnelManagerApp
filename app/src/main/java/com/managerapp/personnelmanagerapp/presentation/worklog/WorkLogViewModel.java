package com.managerapp.personnelmanagerapp.presentation.worklog;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.user.GetWorkLogUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.utils.LocalDataManager;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class WorkLogViewModel extends ViewModel {
    private final String TAG = "WorkLogViewModel";
    private final GetWorkLogUseCase getWorkLogUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final LocalDataManager localDataManager;
    private final MutableLiveData<UiState<List<WorkLogResponse>>> uiState = new MutableLiveData<>();

    @Inject
    public WorkLogViewModel(GetWorkLogUseCase getWorkLogUseCase, LocalDataManager localDataManager) {
        this.getWorkLogUseCase = getWorkLogUseCase;
        this.localDataManager = localDataManager;
    }

    public LiveData<UiState<List<WorkLogResponse>>> getUiState() {
        return uiState;
    }


    public void getWorkLogs() {
        uiState.setValue(UiState.Loading.getInstance());
        disposable.add(
                Single.fromCallable(() -> {
                    long userId = Long.parseLong(localDataManager.getUserId());
                    return userId;
                })
                .flatMap(userId -> getWorkLogUseCase.getWorkLogs(userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> uiState.setValue(UiState.Loading.getInstance()))
                .subscribe(
                        workLogs -> {
                            Log.d(TAG, "Lấy dữ liệu thành công: " + workLogs.toString());
                            uiState.postValue(new UiState.Success(workLogs));
                        },
                        throwable -> {
                            Log.e(TAG, "Lấy dữ liệu thất bại: " + throwable.getMessage());
                            uiState.postValue(new UiState.Error<>(throwable.getMessage()));
                        }
                )
        );
    }
}
