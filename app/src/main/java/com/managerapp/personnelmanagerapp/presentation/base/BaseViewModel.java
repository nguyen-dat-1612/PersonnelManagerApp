package com.managerapp.personnelmanagerapp.presentation.base;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.managerapp.personnelmanagerapp.network.NetworkLiveData;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BaseViewModel extends AndroidViewModel {
    private final NetworkLiveData networkLiveData;

    @Inject
    public BaseViewModel(Application application) {
        super(application);
        networkLiveData = new NetworkLiveData(application);
    }

    public LiveData<Boolean> getNetworkStatus() {
        return networkLiveData;
    }
}
