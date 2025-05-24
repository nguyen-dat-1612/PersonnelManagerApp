package com.managerapp.personnelmanagerapp.presentation.notification.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentHistoryNotificationBinding;
import com.managerapp.personnelmanagerapp.presentation.notification.adapter.HistoryNotificationAdapter;
import com.managerapp.personnelmanagerapp.presentation.notification.viewmodel.HistoryViewModel;
import java.util.ArrayList;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryNotificationFragment extends Fragment {
    private FragmentHistoryNotificationBinding binding;
    private HistoryViewModel viewModel;
    private HistoryNotificationAdapter adapter;
    private int currentPage = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryNotificationBinding.inflate(inflater, container, false);
        setupRecyclerView();
        setupListener();
        observeData();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new HistoryNotificationAdapter(notification -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
            HistoryNotificationFragmentDirections.ActionHistoryNotificationFragmentToDetailNotificationFragment action =
                    HistoryNotificationFragmentDirections.actionHistoryNotificationFragmentToDetailNotificationFragment(notification.getId());
            navController.navigate(action);
        });

        binding.notificationList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.notificationList.setAdapter(adapter);
    }

    private void setupListener() {
        binding.notificationList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!viewModel.getLoading().getValue() &&
                        (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0) {

                    currentPage++;
                    viewModel.loadNextPage(currentPage);
                    adapter.addLoadingFooter(); // Hiển thị loading indicator
                }
            }
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            currentPage = 0;
            viewModel.initLoad("ALL");
            binding.swipeRefresh.setRefreshing(false);
        });

        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
            navController.popBackStack();
        });
    }

    private void observeData() {
        viewModel.getNotificationList().observe(getViewLifecycleOwner(), list -> {
            if (currentPage == 0) {
                adapter.updateList(new ArrayList<>(list));
            } else {
                adapter.removeLoadingFooter();
                adapter.addAll(list);
            }
            if (list.isEmpty()) {
                binding.notificationList.setVisibility(View.GONE);
                binding.emptyView.setVisibility(View.VISIBLE);
            } else {
                binding.notificationList.setVisibility(View.VISIBLE);
                binding.emptyView.setVisibility(View.GONE);
            }
        });
        viewModel.getLoading().observe(getViewLifecycleOwner(), loading -> {
            binding.progressBar.getRoot().setVisibility(loading && currentPage == 0 ? View.VISIBLE : View.GONE);
        });


        viewModel.initLoad(binding.spinnerNotifications.getSelectedItem().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
