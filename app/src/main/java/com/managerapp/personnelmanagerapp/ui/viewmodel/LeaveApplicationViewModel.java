package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.GetLeaveApplications;
import com.managerapp.personnelmanagerapp.ui.state.LeaveApplicationState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LeaveApplicationViewModel extends ViewModel {

    private final GetLeaveApplications getLeaveApplications;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();;

    private final MutableLiveData<LeaveApplicationState> leaveApplicationState = new MutableLiveData<>();

    @Inject
    public LeaveApplicationViewModel(GetLeaveApplications getLeaveApplications) {
        this.getLeaveApplications = getLeaveApplications;
    }

    public MutableLiveData<LeaveApplicationState> getLeaveApplicationState() {
        return leaveApplicationState;
    }

    public void loadLeaveAppliations(){
        leaveApplicationState.postValue(new LeaveApplicationState.Loading());
        compositeDisposable.add(
                getLeaveApplications.excute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                leaveApplications -> {
                                    if (leaveApplications.isEmpty()) {
                                        leaveApplicationState.postValue(new LeaveApplicationState.Empty());
                                    } else {
                                        leaveApplicationState.postValue(new LeaveApplicationState.Success(leaveApplications));
                                    }
                                },
                                throwable -> leaveApplicationState.postValue(new LeaveApplicationState.Error(throwable.getMessage()))
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
