package com.managerapp.personnelmanagerapp.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.managerapp.personnelmanagerapp.utils.NetworkLiveData;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends AndroidViewModel {
    private final NetworkLiveData networkLiveData;

    @Inject
    public MainViewModel(Application application) {
        super(application);
        networkLiveData = new NetworkLiveData(application);
    }

    public LiveData<Boolean> getNetworkStatus() {
        return networkLiveData;
    }
}
