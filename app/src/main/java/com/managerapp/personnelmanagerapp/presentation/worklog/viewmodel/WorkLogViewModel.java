package com.managerapp.personnelmanagerapp.presentation.worklog.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.user.GetWorkLogUseCase;
import com.managerapp.personnelmanagerapp.presentation.worklog.ui.WorkLogUiState;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class WorkLogViewModel extends ViewModel {
    private static final String TAG = "WorkLogViewModel";
    private static final long API_TIMEOUT_SECONDS = 10;

    private final GetWorkLogUseCase getWorkLogUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<WorkLogUiState> uiState =
            new MutableLiveData<>(new WorkLogUiState(true, null, null));

    @Inject
    public WorkLogViewModel(GetWorkLogUseCase getWorkLogUseCase) {
        this.getWorkLogUseCase = getWorkLogUseCase;
        fetchWorkLogs();
    }

    public LiveData<WorkLogUiState> getUiState() {
        return uiState;
    }

    public void refreshData() {
        fetchWorkLogs();
    }

    private void fetchWorkLogs() {
        uiState.setValue(new WorkLogUiState(true, null, null));

        disposables.add(getWorkLogUseCase.getWorkLogs()
                .timeout(API_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        workLogs -> handleSuccess(workLogs),
                        throwable ->  uiState.setValue(new WorkLogUiState(false, Collections.emptyList(), throwable.getMessage()))
                ));
    }

    private void handleSuccess(List<WorkLogResponse> workLogs) {
        if (workLogs.isEmpty()) {
            uiState.setValue(new WorkLogUiState(false, Collections.emptyList(), "No data found"));
        } else {
            uiState.setValue(new WorkLogUiState(false, workLogs, null));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
