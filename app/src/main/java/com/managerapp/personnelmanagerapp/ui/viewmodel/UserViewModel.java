package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.managerapp.personnelmanagerapp.domain.usecase.GetUserUseCase;
import com.managerapp.personnelmanagerapp.ui.state.ProfileInfoState;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class UserViewModel extends ViewModel {
    private final GetUserUseCase getUserUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ProfileInfoState> profileInfoState = new MutableLiveData<>();

    @Inject
    public UserViewModel(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    public MutableLiveData<ProfileInfoState> getProfileInfoState() {
        return profileInfoState;
    }

    public void loadUser(){
        profileInfoState.setValue(ProfileInfoState.Loading.getInstance());
        disposables.add(
                getUserUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( user -> {
                                    if (user != null) {
                                        profileInfoState.postValue(new ProfileInfoState.Success(user));
                                    } else {
                                        profileInfoState.postValue(new ProfileInfoState.Error("User not found"));
                                    }
                                },
                                throwable -> new ProfileInfoState.Error(throwable.getMessage())
                        )
        );
    }
}
