package com.managerapp.personnelmanagerapp.presentation.main;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientEntity;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentNotificationsBinding;
import com.managerapp.personnelmanagerapp.presentation.notification.NotificationActivity;
import com.managerapp.personnelmanagerapp.presentation.notification.NotificationAdapter;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationsFragment extends BaseFragment {

    private static final String TAG = "NotificationsFragment";

    private FragmentNotificationsBinding binding;
    private NotificationRecipientViewModel viewModel;
    private NotificationAdapter adapter;
    private final List<NotificationRecipientEntity> notificationList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NotificationRecipientViewModel.class);
        viewModel.loadAllNotifications();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        setupRecyclerView();
        setupSwipeToRefresh();
        observeNotificationState();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new NotificationAdapter(notificationList, notification -> {
            Intent intent = new Intent(requireContext(), NotificationActivity.class);
            intent.putExtra("id", notification.getId());
            startActivity(intent);
        });

        binding.notificationList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.notificationList.setAdapter(adapter);
    }

    private void setupSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.loadAllNotifications());
    }

    private void observeNotificationState() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                showLoadingState();
            } else if (state instanceof UiState.Success) {
                binding.swipeRefresh.setRefreshing(false);
                showSuccessState(((UiState.Success<List<NotificationRecipientEntity>>) state).getData());
            } else if (state instanceof UiState.Error) {
                binding.swipeRefresh.setRefreshing(false);
                showErrorState(((UiState.Error) state).getErrorMessage());
            }
        });
    }

    private void showLoadingState() {
        binding.swipeRefresh.setRefreshing(true);
        binding.notificationList.setVisibility(INVISIBLE);
        binding.emptyView.setVisibility(GONE);
    }

    private void showSuccessState(List<NotificationRecipientEntity> data) {
        notificationList.clear();
        notificationList.addAll(data);

        if (notificationList.isEmpty()) {
            binding.emptyView.setVisibility(VISIBLE);
            binding.notificationList.setVisibility(INVISIBLE);
        } else {
            adapter.notifyDataSetChanged();
            binding.emptyView.setVisibility(GONE);
            binding.notificationList.setVisibility(VISIBLE);
        }
    }

    private void showErrorState(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Lỗi khi tải thông báo: " + errorMsg);
        binding.emptyView.setVisibility(VISIBLE);
        binding.notificationList.setVisibility(INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
