package com.managerapp.personnelmanagerapp.presentation.notification.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.usecase.notification.GetSenderNotificationsUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

@HiltViewModel
public class HistoryViewModel extends ViewModel {
    private final GetSenderNotificationsUseCase useCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<List<NotificationRecipient>> notificationList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isLastPage = new MutableLiveData<>(false);

    private final PublishSubject<Integer> pageTrigger = PublishSubject.create();
    private int pageSize = 10;

    private String currentType;

    @Inject
    public HistoryViewModel(GetSenderNotificationsUseCase useCase) {
        this.useCase = useCase;
        setupPaginationFlow();
    }

    private void setupPaginationFlow() {
        disposables.add(
                pageTrigger
                        .concatMap(page -> {
                            isLoading.postValue(true);
                            return useCase.execute(page, pageSize, currentType)
                                    .toObservable();
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                paged -> {
                                    List<NotificationRecipient> current = notificationList.getValue();
                                    current.addAll(paged.getContent());
                                    notificationList.setValue(current);
                                    int currentPage = paged.getPage().getNumber();
                                    int totalPages = paged.getPage().getTotalPages();

                                    isLastPage.setValue(currentPage + 1 >= totalPages);
                                    isLoading.setValue(false);
                                },
                                error -> {
                                    isLoading.setValue(false);
                                }
                        )
        );
    }

    public void initLoad(String type) {
        this.currentType = type;
        notificationList.setValue(new ArrayList<>());
        isLastPage.setValue(false);
        loadNextPage(0);
    }

    public void loadNextPage(int page) {
        if (Boolean.TRUE.equals(isLastPage.getValue()) || Boolean.TRUE.equals(isLoading.getValue())) return;
        pageTrigger.onNext(page);
    }

    public LiveData<List<NotificationRecipient>> getNotificationList() {
        return notificationList;
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
