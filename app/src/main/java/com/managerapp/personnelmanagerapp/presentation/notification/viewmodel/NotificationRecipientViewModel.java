package com.managerapp.personnelmanagerapp.presentation.notification.viewmodel;


import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;
import com.managerapp.personnelmanagerapp.domain.usecase.notification.GetAllUserNotificationsUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class NotificationRecipientViewModel extends ViewModel {
    private static final String TAG = "NotificationViewModel";
    private static final int PAGE_SIZE = 10;

    private final GetAllUserNotificationsUseCase getAllNotificationsUseCase;
    private final LocalDataManager localDataManager;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<UiState<PagedModel<NotificationRecipient>>> uiState = new MutableLiveData<>();

    public LiveData<UiState<PagedModel<NotificationRecipient>>> getUiState() {
        return uiState;
    }

    private long userId = -1;
    private int currentPage = 0;
    private boolean isLastPage = false;
    private boolean isLoading = false;

    public GetAllUserNotificationsUseCase getGetAllNotificationsUseCase() {
        return getAllNotificationsUseCase;
    }

    public LocalDataManager getLocalDataManager() {
        return localDataManager;
    }

    public CompositeDisposable getDisposables() {
        return disposables;
    }

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
    public NotificationRecipientViewModel(GetAllUserNotificationsUseCase getAllNotificationsUseCase,
                                          LocalDataManager localDataManager) {
        this.getAllNotificationsUseCase = getAllNotificationsUseCase;
        this.localDataManager = localDataManager;

        try {
            this.userId = localDataManager.getUserId();
        } catch (NumberFormatException e) {
            Log.e(TAG, "Lỗi parse userId: " + e.getMessage());
        }
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
                getAllNotificationsUseCase.execute(userId, currentPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                pagedModel -> {
                                    isLoading = false;
                                    isLastPage = currentPage >= pagedModel.getPage().getTotalPages() - 1;

                                    if (pagedModel.getContent().isEmpty() && currentPage == 0) {
                                        Log.d(TAG, "Danh sách thông báo trống");
                                        uiState.postValue(new UiState.Error("Danh sách thông báo trống"));
                                    } else {
                                        uiState.postValue(new UiState.Success(pagedModel));
                                        Log.d(TAG, "Đã tải trang " + currentPage);
                                    }
                                },
                                throwable -> {
                                    isLoading = false;
                                    Log.e(TAG, "Đã có lỗi xảy ra: " + throwable.getMessage());
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