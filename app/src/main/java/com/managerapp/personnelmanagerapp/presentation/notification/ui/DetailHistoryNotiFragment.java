package com.managerapp.personnelmanagerapp.presentation.notification.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.managerapp.personnelmanagerapp.databinding.FragmentDetailNotificationBinding;
import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.presentation.notification.adapter.AttachmentAdapter;
import com.managerapp.personnelmanagerapp.presentation.notification.viewmodel.DetailHistoryViewModel;
import com.managerapp.personnelmanagerapp.presentation.notification.viewmodel.NotificationViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailHistoryNotiFragment extends Fragment {

    private DetailHistoryViewModel notificationViewModel;
    private FragmentDetailNotificationBinding binding;
    private long notificationId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationViewModel = new ViewModelProvider(this).get(DetailHistoryViewModel.class);
        DetailHistoryNotiFragmentArgs args = DetailHistoryNotiFragmentArgs.fromBundle(getArguments());
        notificationId = args.getNotificationId();
        notificationViewModel.loadNotification(notificationId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailNotificationBinding.inflate(inflater, container, false);
        loadNotification();
        binding.swipeRefresh.setOnRefreshListener(() -> {
            notificationViewModel.loadNotification(notificationId);
        });

        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
            navController.popBackStack();
        });
        return binding.getRoot();
    }

    private void loadNotification() {
        notificationViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
            } else if (state instanceof  UiState.Success) {
                Notification notification = ((UiState.Success<Notification>) state).getData();

                binding.swipeRefresh.setRefreshing(false);
                binding.tvNotificationTitle.setText(notification.getTitle());
                binding.tvSendDate.setText(notification.getSendDate());
                binding.tvSenderName.setText(notification.getSender().getFullName());
                binding.tvNotificationContent.setText(notification.getContent());
                binding.rvAttachments.setLayoutManager(new LinearLayoutManager(getContext()));
                AttachmentAdapter adapter = new AttachmentAdapter(
                        notification.getAttached(),
                        requireContext()
                );
                binding.rvAttachments.setAdapter(adapter);
            } else if (state instanceof  UiState.Error) {
                binding.swipeRefresh.setRefreshing(false);
            }
        });
    }
}