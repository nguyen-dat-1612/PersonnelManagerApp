package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.GetUserUseCase;
import com.managerapp.personnelmanagerapp.ui.state.HomeState;
import com.managerapp.personnelmanagerapp.ui.state.ProfileInfoState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final GetUserUseCase getUserUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<HomeState> homeState = new MutableLiveData<>();

    @Inject
    public HomeViewModel(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    public MutableLiveData<HomeState> getHomeState() {
        return homeState;
    }

    public void loadUser(){
        homeState.setValue(HomeState.Loading.getInstance());
        disposable.add(
                getUserUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( user -> {
                                    if (user != null) {
                                        homeState.postValue(new HomeState.Success(user));
                                    } else {
                                        homeState.postValue(new HomeState.Error("UserEntity not found"));
                                    }
                                },
                                throwable -> homeState.postValue( new HomeState.Error(throwable.getMessage()))
                        )
        );
    }

    @Override
    protected void onCleared() {
        disposable.dispose();  // Thêm phương thức này
        super.onCleared();
    }
}
