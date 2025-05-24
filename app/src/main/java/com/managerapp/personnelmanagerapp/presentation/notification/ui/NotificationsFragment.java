package com.managerapp.personnelmanagerapp.presentation.notification.ui;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.managerapp.personnelmanagerapp.databinding.FragmentNotificationsBinding;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;
import com.managerapp.personnelmanagerapp.presentation.main.ui.MainFragmentDirections;
import com.managerapp.personnelmanagerapp.presentation.notification.viewmodel.NotificationRecipientViewModel;
import com.managerapp.personnelmanagerapp.presentation.notification.adapter.NotificationAdapter;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.List;
import dagger.hilt.android.AndroidEntryPoint;
import com.managerapp.personnelmanagerapp.R;

@AndroidEntryPoint
public class NotificationsFragment extends Fragment {
    private static final String TAG = "NotificationsFragment";

    private FragmentNotificationsBinding binding;
    private NotificationRecipientViewModel viewModel;
    private NotificationAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NotificationRecipientViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        setupRecyclerView();
        setupSwipeToRefresh();
        setupScrollListener();
        observeNotificationState();
        viewModel.loadFirstPage();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new NotificationAdapter(notification -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
            MainFragmentDirections.ActionMainFragmentToDetailNotificationFragment action =
                    MainFragmentDirections.actionMainFragmentToDetailNotificationFragment(notification.getId(), notification.isReadStatus());
            navController.navigate(action);
        });

        layoutManager = new LinearLayoutManager(requireContext());
        binding.notificationList.setLayoutManager(layoutManager);
        binding.notificationList.setAdapter(adapter);
    }

    private void setupSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.loadFirstPage();
        });
    }

    private void setupScrollListener() {
        binding.notificationList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!viewModel.isLoading() && !viewModel.isLastPage()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= viewModel.getCurrentPage()) {
                        viewModel.loadNextPage();
                    }
                }
            }
        });
    }

    private void observeNotificationState() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                showLoadingState();
            } else if (state instanceof UiState.Success) {
                binding.swipeRefresh.setRefreshing(false);
                PagedModel<NotificationRecipient> pagedModel =
                        ((UiState.Success<PagedModel<NotificationRecipient>>) state).getData();
                showSuccessState(pagedModel);
            } else if (state instanceof UiState.Error) {
                binding.swipeRefresh.setRefreshing(false);
                showErrorState(((UiState.Error) state).getErrorMessage());
            }
        });
    }

    private void showLoadingState() {
        if (viewModel.getCurrentPage() == 0) {
            binding.swipeRefresh.setRefreshing(true);
            binding.notificationList.setVisibility(INVISIBLE);
            binding.emptyView.setVisibility(GONE);
        } else {
            adapter.showLoading(true);
        }
    }

    private void showSuccessState(PagedModel<NotificationRecipient> pagedModel) {
        List<NotificationRecipient> content = pagedModel.getContent();

        if (viewModel.getCurrentPage() == 0) {
            adapter.updateList(content);
        } else {
            adapter.addItems(content);
        }

        if (adapter.getItemCount() == 0) {
            binding.emptyView.setVisibility(VISIBLE);
            binding.notificationList.setVisibility(INVISIBLE);
        } else {
            binding.emptyView.setVisibility(GONE);
            binding.notificationList.setVisibility(VISIBLE);
        }

        adapter.showLoading(false);
    }

    private void showErrorState(String errorMsg) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Lỗi khi tải thông báo: " + errorMsg);

        if (adapter.getItemCount() == 0) {
            binding.emptyView.setVisibility(VISIBLE);
            binding.notificationList.setVisibility(INVISIBLE);
        }

        adapter.showLoading(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}