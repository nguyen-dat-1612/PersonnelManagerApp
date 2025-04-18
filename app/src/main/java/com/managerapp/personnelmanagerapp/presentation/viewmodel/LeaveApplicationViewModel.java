package com.managerapp.personnelmanagerapp.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.GetLeaveApplications;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LeaveApplicationViewModel extends ViewModel {

    private final GetLeaveApplications getLeaveApplications;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();;

    private final MutableLiveData<UiState<List<LeaveApplicationResponse>>> uiState = new MutableLiveData<>();

    @Inject
    public LeaveApplicationViewModel(GetLeaveApplications getLeaveApplications) {
        this.getLeaveApplications = getLeaveApplications;
    }

    public LiveData<UiState<List<LeaveApplicationResponse>>> getUiState() {
        return uiState;
    }

    public void loadLeaveAppliations(int userId){
        uiState.setValue(UiState.Loading.getInstance());
        compositeDisposable.add(
                getLeaveApplications.excute(userId)
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
