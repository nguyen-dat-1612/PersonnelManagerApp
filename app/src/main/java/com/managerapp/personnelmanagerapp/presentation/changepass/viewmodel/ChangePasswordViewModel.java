package com.managerapp.personnelmanagerapp.presentation.changepass.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.user.ChangePasswordUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ChangePasswordViewModel extends ViewModel {
    private final ChangePasswordUseCase changePasswordUseCase;
    private final CompositeDisposable composite = new CompositeDisposable();
    private final MutableLiveData<UiState<Boolean>> changePasswordState = new MutableLiveData<>();
    public LiveData<UiState<Boolean>> getChangePasswordState() {
        return changePasswordState;
    }

    @Inject
    public ChangePasswordViewModel(ChangePasswordUseCase changePasswordUseCase) {
        this.changePasswordUseCase = changePasswordUseCase;
    }

    public void loadChangePassword(String oldPass, String newPass) {
        changePasswordState.setValue(UiState.Loading.getInstance());
        composite.add(
                changePasswordUseCase.execute(oldPass, newPass)
                        .timeout(5, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    if (result) {
                                        changePasswordState.setValue(new UiState.Success<>(true));
                                    }
                                },
                                throwable -> changePasswordState.setValue(new UiState.Error<>(throwable.getMessage()))
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}
