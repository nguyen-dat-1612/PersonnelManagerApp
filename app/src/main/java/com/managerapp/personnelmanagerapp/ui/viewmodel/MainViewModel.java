package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.GetUserUseCase;
import com.managerapp.personnelmanagerapp.ui.state.MainState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MainViewModel extends ViewModel {
    private final GetUserUseCase getUserUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<MainState> mainState = new MutableLiveData<>();

    @Inject
    public MainViewModel(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    public MutableLiveData<MainState> getMainState() {
        return mainState;
    }

    public void loadUser(){
        mainState.setValue(MainState.Loading.getInstance());
        disposable.add(
                getUserUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( user -> {
                                    if (user != null) {
                                        mainState.postValue(new MainState.Success(user));
                                    } else {
                                        mainState.postValue(new MainState.Error("UserEntity not found"));
                                    }
                                },
                                throwable -> mainState.postValue( new MainState.Error(throwable.getMessage()))
                        )
        );
    }

    @Override
    protected void onCleared() {
        disposable.dispose();  // Thêm phương thức này
        super.onCleared();
    }
}
