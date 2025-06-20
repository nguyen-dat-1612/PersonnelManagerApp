package com.managerapp.personnelmanagerapp.presentation.leaveapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.leaveapp.GetLeaveApplications;
import com.managerapp.personnelmanagerapp.domain.usecase.user.GetUserUseCase;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.state.CreateLeaveUiState;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.state.LeaveHistoryUiState;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LeaveApplicationViewModel extends ViewModel {
    private final GetLeaveApplications getLeaveApplications;
    private final GetUserUseCase getUserProfileUseCase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<LeaveHistoryUiState> uiState = new MutableLiveData<>();


    @Inject
    public LeaveApplicationViewModel(GetLeaveApplications getLeaveApplications, GetUserUseCase getUserProfile) {
        this.getLeaveApplications = getLeaveApplications;
        this.getUserProfileUseCase = getUserProfile;
        loadLeaveApplications();
    }


    public LiveData<LeaveHistoryUiState> getLeaveApplications() {
        return uiState;
    }

    public void refreshLeaveApplications() {
        loadLeaveApplications();
    }

    public void loadLeaveApplications(){
        uiState.setValue(LeaveHistoryUiState.loading());
        compositeDisposable.add(
                getLeaveApplications.execute()
                        .subscribeOn(Schedulers.io())
                        .timeout(5, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                leaveApplications -> {
                                    uiState.setValue(LeaveHistoryUiState.success(leaveApplications));
                                },
                                throwable -> {
                                    Log.e("LeaveApplicationViewModel", "Error loading leave applications", throwable);
                                    uiState.setValue(LeaveHistoryUiState.error(throwable.getMessage()));
                                }
                        )
        );
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
