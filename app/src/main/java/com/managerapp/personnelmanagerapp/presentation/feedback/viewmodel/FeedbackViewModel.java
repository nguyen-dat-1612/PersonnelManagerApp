package com.managerapp.personnelmanagerapp.presentation.feedback.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.domain.usecase.feedback.SendFeedbackUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

@HiltViewModel
public class FeedbackViewModel extends ViewModel {
    private final SendFeedbackUseCase sendFeedbackUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<UiState<String>> uiState = new MutableLiveData<>();
    public MutableLiveData<UiState<String>> getUiState() {
        return uiState;
    }
    private Context context;

    @Inject
    public FeedbackViewModel(@ApplicationContext Context context , SendFeedbackUseCase sendFeedbackUseCase) {
        this.context = context;
        this.sendFeedbackUseCase = sendFeedbackUseCase;
    }

    public void sendFeedback(String title, String content) {
        uiState.setValue(UiState.Loading.getInstance());

        disposables.add(sendFeedbackUseCase.execute(title, content)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .timeout(5, TimeUnit.SECONDS)
                        .doOnSubscribe(disposable -> uiState.setValue(UiState.Loading.getInstance()))
                        .subscribe(
                                response -> {
                                    if (response != null) {
                                        uiState.setValue(new UiState.Success(context.getString(R.string.feedback_send_success)));
                                    } else {
                                        uiState.setValue(new UiState.Error(context.getString(R.string.feedback_send_failed)));
                                    }
                                },
                                throwable -> {
                                    String errorMsg = context.getString(R.string.feedback_send_error, throwable.getMessage());
                                    uiState.setValue(new UiState.Error(errorMsg));
                                }
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}

