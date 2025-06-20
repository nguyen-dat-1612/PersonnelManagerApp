package com.managerapp.personnelmanagerapp.presentation.notification.viewmodel;


import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;
import com.managerapp.personnelmanagerapp.domain.model.ReadEnum;
import com.managerapp.personnelmanagerapp.domain.usecase.notification.GetAllUserNotificationsUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class NotificationRecipientViewModel extends ViewModel {
    private static final int PAGE_SIZE = 10;
    private final GetAllUserNotificationsUseCase getAllNotificationsUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<UiState<PagedModel<NotificationRecipient>>> uiState = new MutableLiveData<>();

    public LiveData<UiState<PagedModel<NotificationRecipient>>> getUiState() {
        return uiState;
    }

    private long userId = -1;
    private int currentPage = 0;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private MutableLiveData<ReadEnum> selectedFilter = new MutableLiveData<>();

    public long getUserId() {
        return userId;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public boolean isLastPage() {
        return isLastPage;
    }
    public boolean isLoading() {
        return isLoading;
    }

    @Inject
    public NotificationRecipientViewModel(GetAllUserNotificationsUseCase getAllNotificationsUseCase) {
        this.getAllNotificationsUseCase = getAllNotificationsUseCase;
    }

    public LiveData<ReadEnum> getSelectedFilter() { return selectedFilter; }

    public void setSelectedFilter(ReadEnum filter) {
        this.selectedFilter.setValue(filter);
        loadFirstPage();
    }

    public void loadFirstPage() {
        currentPage = 0;
        isLastPage = false;
        loadNotifications();
    }

    public void loadNextPage() {
        if (!isLoading && !isLastPage) {
            currentPage++;
            loadNotifications();
        }
    }

    private void loadNotifications() {
        isLoading = true;
        uiState.setValue(UiState.Loading.getInstance());

        disposables.add(
                getAllNotificationsUseCase.execute(currentPage)
                        .subscribeOn(Schedulers.io())
                        .map(pagedModel -> {
                            ReadEnum filter = selectedFilter.getValue();

                            List<NotificationRecipient> filteredList = pagedModel.getContent().stream()
                                    .filter(notification -> {
                                        if (filter == ReadEnum.READ) {
                                            return notification.isReadStatus();
                                        } else if (filter == ReadEnum.UNREAD) {
                                            return !notification.isReadStatus();
                                        }
                                        return true;
                                    })
                                    .collect(Collectors.toList());

                            PagedModel<NotificationRecipient> filteredPagedModel = new PagedModel<>();
                            filteredPagedModel.setContent(filteredList);
                            filteredPagedModel.setPage(pagedModel.getPage());

                            return filteredPagedModel;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                pagedModel -> {
                                    isLoading = false;
                                    isLastPage = currentPage >= pagedModel.getPage().getTotalPages() - 1;

                                    if (pagedModel.getContent().isEmpty() && currentPage == 0) {
                                        uiState.postValue(new UiState.Error("Danh sách thông báo trống"));
                                    } else {
                                        uiState.postValue(new UiState.Success(pagedModel));
                                    }
                                },
                                throwable -> {
                                    isLoading = false;
                                    uiState.postValue(new UiState.Error(throwable.getMessage()));
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