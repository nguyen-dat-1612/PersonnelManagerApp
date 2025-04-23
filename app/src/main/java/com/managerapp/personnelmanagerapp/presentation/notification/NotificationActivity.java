package com.managerapp.personnelmanagerapp.presentation.notification;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.managerapp.personnelmanagerapp.databinding.ActivityNotificationBinding;
import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationActivity extends BaseActivity {
    private static final String TAG = "NotificationActivity";
    private NotificationViewModel notificationViewModel;
    private ActivityNotificationBinding binding;
    private long notificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        // Nhận id từ Intent
        if (getIntent() != null && getIntent().hasExtra("id")) {
            notificationId = getIntent().getLongExtra("id", -1L);
            notificationViewModel.loadNotification(notificationId);
        } else {
            finish();
        }

        loadNotification();

        binding.swipeRefresh.setOnRefreshListener(() -> {
            notificationViewModel.loadNotification(notificationId);
        });

        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void loadNotification() {
        notificationViewModel.getUiState().observe(this, state -> {
            if (state instanceof UiState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
                binding.content.setVisibility(GONE);
            } else if (state instanceof  UiState.Success) {
                Notification notification = ((UiState.Success<Notification>) state).getData();
                binding.swipeRefresh.setRefreshing(false);
                binding.setNotification(notification);
                binding.content.setVisibility(VISIBLE);
            } else if (state instanceof  UiState.Error) {
                binding.swipeRefresh.setRefreshing(false);
                binding.content.setVisibility(GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        // Nếu có LiveData observer, nên remove ở đây
    }

}