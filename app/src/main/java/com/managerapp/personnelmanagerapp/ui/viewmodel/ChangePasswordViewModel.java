package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.ChangePasswordUseCase;
import com.managerapp.personnelmanagerapp.ui.state.ChangePasswordState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ChangePasswordViewModel extends ViewModel {
    private final ChangePasswordUseCase changePasswordUseCase;
    private final CompositeDisposable composite = new CompositeDisposable();
    private MutableLiveData<ChangePasswordState> changePasswordState = new MutableLiveData<>();

    @Inject
    public ChangePasswordViewModel(ChangePasswordUseCase changePasswordUseCase) {
        this.changePasswordUseCase = changePasswordUseCase;
    }

    public LiveData<ChangePasswordState> getChangePasswordState() {
        return changePasswordState;
    }

    public void loadChangePassword(int userId,String oldPass, String newPass) {
        changePasswordState.setValue(ChangePasswordState.Loading.getInstance());

        composite.add(changePasswordUseCase.excute(userId, oldPass, newPass)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response) {
                                changePasswordState.postValue(new ChangePasswordState.Success("Đổi mật khẩu thành công"));
                            } else {
                                changePasswordState.postValue(new ChangePasswordState.Error("Đổi mật khẩu thất bại"));
                            }
                        },
                        throwable -> changePasswordState.postValue(new ChangePasswordState.Error("Lỗi hệ thống: " + throwable.getMessage()))
                )
        );
    }
}
