package com.managerapp.personnelmanagerapp.presentation.fragments;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientEntity;
import com.managerapp.personnelmanagerapp.presentation.activities.NotificationActivity;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentNotificationsBinding;
import com.managerapp.personnelmanagerapp.presentation.adapters.NotificationAdapter;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.viewmodel.NotificationRecipientViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationsFragment extends BaseFragment {

    private static final String TAG = "NotificationsFragment";
    private FragmentNotificationsBinding binding;
    private List<NotificationRecipientEntity> notificationList = new ArrayList<>();
    private NotificationAdapter adapter;
    private NotificationRecipientViewModel viewModel;

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
        viewModel = new ViewModelProvider(requireActivity()).get(NotificationRecipientViewModel.class);

        adapter = new NotificationAdapter(notificationList, notification -> {
            Toast.makeText(requireContext(), "Clicked: " + notification.getId(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireContext(), NotificationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("id", notification.getId());
            intent.putExtras(bundle);
            startActivity(intent);
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
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            binding.swipeRefresh.setRefreshing(false);
            if (state instanceof UiState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
                binding.notificationList.setVisibility(INVISIBLE);
            } else if (state instanceof UiState.Success) {
                notificationList.clear();
                List<NotificationRecipientEntity> newNotifications = ((UiState.Success<List<NotificationRecipientEntity>>) state).getData();
                notificationList.addAll(newNotifications);

                if (notificationList.isEmpty()) {
                    Log.d(TAG, "Danh sách thông báo trống");
                    binding.emptyView.setVisibility(VISIBLE);
                    binding.notificationList.setVisibility(INVISIBLE);
                } else {
                    adapter.notifyDataSetChanged();
                    binding.emptyView.setVisibility(GONE);
                    binding.notificationList.setVisibility(VISIBLE);
                }

            } else if (state instanceof UiState.Error) {
                String errorMsg = ((UiState.Error) state).getErrorMessage();
                Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Lỗi khi tải thông báo: " + errorMsg);
                binding.emptyView.setVisibility(VISIBLE);
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