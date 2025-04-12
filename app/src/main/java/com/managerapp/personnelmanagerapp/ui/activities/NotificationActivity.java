package com.managerapp.personnelmanagerapp.ui.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityNotificationBinding;
import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.ui.base.BaseActivity;
import com.managerapp.personnelmanagerapp.ui.state.NotificationState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.NotificationViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationActivity extends BaseActivity {
    private static final String TAG = "NotificationActivity";
    private NotificationViewModel notificationViewModel;
    private long id;
    private ActivityNotificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getBundle();

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        loadNotification();

        binding.swipeRefresh.setOnRefreshListener(() -> {
            loadNotification();
        });

        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
    }


    private void getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            id = bundle.getLong("id");
        } else {
            Log.e(TAG, "Lấy dữ liệu không thành công"); // Bây giờ TAG đã được khai báo
        }
    }

    private void loadNotification() {
        notificationViewModel.getNotificationState().observe(this, state -> {
            if (state instanceof NotificationState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
                binding.content.setVisibility(GONE);
            } else if (state instanceof  NotificationState.Success) {
                Notification notification = ((NotificationState.Success) state).getNotification();
                binding.swipeRefresh.setRefreshing(false);
                binding.setNotification(notification);
                binding.content.setVisibility(VISIBLE);
            } else if (state instanceof  NotificationState.Error) {
                binding.swipeRefresh.setRefreshing(false);
                binding.content.setVisibility(GONE);
            }
        });
        notificationViewModel.loadNotification(this.id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
        // Nếu có LiveData observer, nên remove ở đây
    }

}