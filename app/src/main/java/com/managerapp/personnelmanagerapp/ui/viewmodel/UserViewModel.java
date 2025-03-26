package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.model.User;
import com.managerapp.personnelmanagerapp.domain.usecase.GetUserUseCase;
import io.reactivex.rxjava3.functions.Consumer;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class UserViewModel extends ViewModel {
    private final GetUserUseCase getUserUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public UserViewModel(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    public void loadUser(int userId, Consumer<User> onSuccess, Consumer<Throwable> onError){
        disposables.add(
                getUserUseCase.execute(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onSuccess, onError)
        );
    }
}
