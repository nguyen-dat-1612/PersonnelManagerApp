package com.managerapp.personnelmanagerapp.presentation.leaveapp.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentRequestHistoryBinding;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.adapter.LeaveApplicationAdapter;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.viewmodel.LeaveApplicationViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import dagger.hilt.android.AndroidEntryPoint;
@AndroidEntryPoint
public class LeaveHistoryFragment extends BaseFragment {
    private FragmentRequestHistoryBinding binding;
    private LeaveApplicationAdapter adapter;
    private LeaveApplicationViewModel viewModel;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LeaveApplicationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRequestHistoryBinding.inflate(inflater, container, false);
        setupRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController =  Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        setupListeners();
        loadLeaveApplication();
    }

    private void setupRecyclerView() {
        adapter = new LeaveApplicationAdapter(requireActivity());
        binding.recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewHistory.setAdapter(adapter);

    }

    private void setupListeners() {
        binding.swipeRefresh.setOnRefreshListener(()-> {
            binding.swipeRefresh.setRefreshing(false);
            viewModel.refreshLeaveApplications();
        });
        binding.fabNewRequest.setOnClickListener(v -> navigateToNewRequest());

        binding.backBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_requestHistoryFragment_to_mainFragment);
            navController.popBackStack();
        });
    }

    private void navigateToNewRequest() {
        navController.navigate(R.id.action_requestHistoryFragment_to_newLeaveAppFragment);
    }

    private void loadLeaveApplication() {
        viewModel.getLeaveApplications().observe(getViewLifecycleOwner(), state -> {

            binding.progressOverlay.getRoot().setVisibility(
                    state.isLoading()  ? VISIBLE : GONE
            );

            if (state.getLeaveApplications() != null) {
                if (state.getLeaveApplications().isEmpty()) {
                    binding.emptyAnimation.getRoot().setVisibility(VISIBLE);
                } else {
                    binding.approvedCount.setText(String.valueOf(state.getApprovedCount()));
                    binding.pendingCount.setText(String.valueOf(state.getPendingCount()));
                    binding.rejectedCount.setText(String.valueOf(state.getRejectedCount()));
                    adapter.submitList(state.getLeaveApplications());
                }
            }

        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}