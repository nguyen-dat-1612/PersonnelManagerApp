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
import com.managerapp.personnelmanagerapp.presentation.notification.viewmodel.NotificationViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.utils.DateTimeUtils;
import com.managerapp.personnelmanagerapp.utils.PdfUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailNotificationFragment extends Fragment {
    private static final String TAG = "DetailNotificationFragment";
    private NotificationViewModel notificationViewModel;
    private FragmentDetailNotificationBinding binding;
    private long notificationId;
    private boolean isRead = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        DetailNotificationFragmentArgs args = DetailNotificationFragmentArgs.fromBundle(getArguments());
        notificationId = args.getNotificationId();
        isRead = args.getIsReadStatus();
        Log.d(TAG, "onCreate: " + notificationId + " " + isRead);
        notificationViewModel.loadNotification(notificationId);
        if (!isRead) {
            notificationViewModel.markAsRead(notificationId);
        }
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
                binding.tvSendDate.setText(DateTimeUtils.formatSendDate(notification.getSendDate()));
                binding.tvSenderName.setText(notification.getSender().getFullName());
                binding.tvNotificationContent.setText(notification.getContent());
                binding.rvAttachments.setLayoutManager(new LinearLayoutManager(getContext()));
                AttachmentAdapter adapter = new AttachmentAdapter(
                        notification.getAttached(), requireContext()
                );
                binding.rvAttachments.setAdapter(adapter);
            } else if (state instanceof  UiState.Error) {
                binding.swipeRefresh.setRefreshing(false);
                Log.d(TAG, "loadNotification: " + ((UiState.Error) state).getErrorMessage());
            }
        });
    }
}