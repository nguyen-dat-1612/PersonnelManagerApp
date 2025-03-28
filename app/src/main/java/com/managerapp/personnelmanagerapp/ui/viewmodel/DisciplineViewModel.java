package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.ui.state.DisciplineState;

import dagger.hilt.android.HiltAndroidApp;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
//
//@HiltAndroidApp
//public class DisciplineViewModel extends ViewModel {
//
//    private final CompositeDisposable composite = new CompositeDisposable();
//    private MutableLiveData<DisciplineState> disciplineState = new MutableLiveData<>();
//
//    public MutableLiveData<DisciplineState> getDisciplineState() {
//        return disciplineState;
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
