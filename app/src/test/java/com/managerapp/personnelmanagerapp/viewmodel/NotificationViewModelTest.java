package com.managerapp.personnelmanagerapp.viewmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.lifecycle.LiveData;

import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.usecase.notification.GetNotificationRecipientUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.notification.MarkNotificationUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.notification.viewmodel.NotificationViewModel;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

public class NotificationViewModelTest {
    @Mock
    GetNotificationRecipientUseCase mockGetNotificationUseCase;

    @Mock
    MarkNotificationUseCase mockMarkNotificationUseCase;

    @Mock
    LocalDataManager mockLocalDataManager;

    private NotificationViewModel viewModel;
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this); // nếu không dùng @RunWith(MockitoJUnitRunner.class)
        viewModel = new NotificationViewModel(
                mockGetNotificationUseCase,
                mockLocalDataManager,
                mockMarkNotificationUseCase
        );
    }

    @Test
    public void loadNotification_success() {
        Notification fakeNotification = new Notification( 1, "daada", "");
        Mockito.when(mockGetNotificationUseCase.execute(1))
                .thenReturn(Single.just(fakeNotification));
        viewModel.loadNotification(1);

        // Assert hoặc verify
        LiveData<UiState<Notification>> liveData = viewModel.getUiState();
        // Kiểm tra kết quả như mong muốn
    }
//
//    @Test
//    public void loadNotification_error() {
//        long notificationId = 999L;
//        String errorMessage = "Not found";
//        when(mockGetNotificationUseCase.execute(notificationId))
//                .thenReturn(Single.error(new Throwable(errorMessage)));
//
//        viewModel.loadNotification(notificationId);
//
//        UiState<Notification> state = viewModel.getUiState().getValue();
//        assertTrue(state instanceof UiState.Error);
//        assertEquals(errorMessage, ((UiState.Error<Notification>) state).getErrorMessage());
//    }

    @After
    public void tearDown() {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }
}
