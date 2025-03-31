package com.managerapp.personnelmanagerapp.ui.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentNotificationsBinding;
import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.ui.adapters.NotificationAdapter;
import com.managerapp.personnelmanagerapp.ui.state.NotificationState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.NotificationViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationsFragment extends BaseFragment {

    private static final String TAG = "NotificationsFragment";
    private FragmentNotificationsBinding binding;
    private List<NotificationRecipient> notificationList = new ArrayList<>();
    private NotificationAdapter adapter;
    private NotificationViewModel viewModel;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        adapter = new NotificationAdapter(notificationList, notification -> {
            Toast.makeText(requireContext(), "Clicked: " + notification.toString(), Toast.LENGTH_SHORT).show();
        });
        binding.notificationList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.notificationList.setAdapter(adapter);

        // Gọi API để lấy danh sách thông báo
        loadNotifications();

        binding.swipeRefresh.setOnRefreshListener(() -> {
            loadNotifications();
        });


        return binding.getRoot();
    }

    private void loadNotifications() {
        viewModel.getNotificationState().observe(getViewLifecycleOwner(), state -> {
            binding.swipeRefresh.setRefreshing(false);

            if (state instanceof NotificationState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
            } else if (state instanceof NotificationState.Success) {
                notificationList.clear();
                List<NotificationRecipient> newNotifications = ((NotificationState.Success) state).notifications;
                notificationList.addAll(newNotifications);

                adapter.notifyDataSetChanged();
                binding.emptyView.setVisibility(GONE);
                binding.notificationList.setVisibility(VISIBLE);
            } else if (state instanceof NotificationState.Error) {
                String errorMsg = ((NotificationState.Error) state).message;
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Lỗi khi tải thông báo: " + errorMsg);
                binding.emptyView.setVisibility(VISIBLE);
            } else if (state instanceof NotificationState.Empty) {
                Log.d(TAG, "Danh sách thông báo trống");
                binding.emptyView.setVisibility(VISIBLE);
                binding.notificationList.setVisibility(GONE);
            }
        });

        viewModel.loadAllNotifications();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}