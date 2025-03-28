package com.managerapp.personnelmanagerapp.ui.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.ui.state.RewardState;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

//@HiltViewModel
//public class RewardViewModel extends ViewModel {
//
//    private CompositeDisposable composite = new CompositeDisposable();
//    private MutableLiveData<RewardState> rewardState = new MutableLiveData<>();
//
//    public LiveData<RewardState> getRewardState() {
//        return rewardState;
//    }
//
//    @Override
//    protected void onCleared() {
//        super.onCleared();
//        if (composite != null) {
//            composite.dispose();
//        }
//    }
//}
