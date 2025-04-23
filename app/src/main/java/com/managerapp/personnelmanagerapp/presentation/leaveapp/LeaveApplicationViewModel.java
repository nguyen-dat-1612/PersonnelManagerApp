package com.managerapp.personnelmanagerapp.presentation.leaveapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.leaveapp.GetLeaveApplications;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.utils.LocalDataManager;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LeaveApplicationViewModel extends ViewModel {

    private final String TAG = "LeaveApplicationViewModel";

    private final GetLeaveApplications getLeaveApplications;
    private final LocalDataManager localDataManager;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<UiState<List<LeaveApplicationResponse>>> uiState = new MutableLiveData<>();

    private int userId = -1;
    @Inject
    public LeaveApplicationViewModel(GetLeaveApplications getLeaveApplications, LocalDataManager localDataManager) {
        this.getLeaveApplications = getLeaveApplications;
        this.localDataManager = localDataManager;

        try {
            this.userId = Integer.parseInt(localDataManager.getUserId());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Lỗi parse userId: " + e.getMessage());
        }
    }

    public LiveData<UiState<List<LeaveApplicationResponse>>> getUiState() {
        return uiState;
    }

    public void loadLeaveAppliations(){
        uiState.setValue(UiState.Loading.getInstance());
        compositeDisposable.add(
                getLeaveApplications.execute(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                leaveApplications -> {
                                    uiState.postValue(new UiState.Success(leaveApplications));
                                },
                                throwable -> uiState.postValue(new UiState.Error(throwable.getMessage()))
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
