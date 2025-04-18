package com.managerapp.personnelmanagerapp.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.model.User;
import com.managerapp.personnelmanagerapp.domain.usecase.GetUserUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class UserViewModel extends ViewModel {
    private final GetUserUseCase getUserUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<UiState<User>> uiState = new MutableLiveData<>();

    @Inject
    public UserViewModel(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    public LiveData<UiState<User>> getUiState() {
        return uiState;
    }

    public void loadUser(){
        uiState.setValue(UiState.Loading.getInstance());
        disposables.add(
                getUserUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( user -> {
                                    if (user != null) {
                                        uiState.postValue(new UiState.Success<>(user));
                                    } else {
                                        uiState.postValue(new UiState.Error("UserEntity not found"));
                                    }
                                },
                                throwable -> new UiState.Error(throwable.getMessage())
                        )
        );
    }
}
