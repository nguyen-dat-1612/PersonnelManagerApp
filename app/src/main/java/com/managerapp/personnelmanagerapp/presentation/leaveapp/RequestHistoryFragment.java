package com.managerapp.personnelmanagerapp.presentation.leaveapp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentRequestHistoryBinding;
import com.managerapp.personnelmanagerapp.presentation.main.MainActivity;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
@AndroidEntryPoint
public class RequestHistoryFragment extends BaseFragment {

    private static final String TAG = "RequestHistoryFragment";
    private FragmentRequestHistoryBinding binding;
    private LeaveApplicationAdapter adapter;
    private LeaveApplicationViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LeaveApplicationViewModel.class);
        viewModel.loadLeaveAppliations();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRequestHistoryBinding.inflate(inflater, container, false);

        adapter = new LeaveApplicationAdapter(new ArrayList<>(), requireActivity());
        setupListeners();
        loadLeaveApplication();
        viewModel.loadLeaveAppliations();
        return binding.getRoot();
    }

    private void setupListeners() {
        binding.swipeRefresh.setOnRefreshListener(()->viewModel.loadLeaveAppliations());
        binding.fabNewRequest.setOnClickListener(v -> navigateToNewRequest());
        binding.backBtn.setOnClickListener(v -> navigateBackToMain());
    }

    private void navigateToNewRequest() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.request_fragment);
        navController.navigate(R.id.action_requestHistoryFragment_to_newRequestFragment);
    }

    private void navigateBackToMain() {
        requireActivity().finish();
    }

    private void loadLeaveApplication() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            binding.swipeRefresh.setRefreshing(state instanceof UiState.Loading);
            if (state instanceof UiState.Success) {
                handleSuccessState(((UiState.Success<List<LeaveApplicationResponse>>) state).getData());
            } else if (state instanceof UiState.Error) {
                handleErrorState(((UiState.Error) state).getErrorMessage());
            }
        });
    }

    private void handleSuccessState(List<LeaveApplicationResponse> data) {
        updateLeaveStats(data);
        binding.recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewHistory.setAdapter(adapter);
        binding.recyclerViewHistory.setVisibility(data.isEmpty() ? GONE : VISIBLE);
        binding.emptyView.setVisibility(data.isEmpty() ? VISIBLE : GONE);
        adapter.updateData(data);
    }

    private void handleErrorState(String errorMessage) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
        binding.emptyView.setVisibility(VISIBLE);
    }

    private void updateLeaveStats(List<LeaveApplicationResponse> applications) {
        int approvedCount = 0, pendingCount = 0, rejectedCount = 0;

        for (LeaveApplicationResponse application : applications) {
            String status = application.getFormStatusEnum();
            if ("APPROVED".equals(status)) approvedCount++;
            else if ("PENDING".equals(status)) pendingCount++;
            else if ("REJECTED".equals(status)) rejectedCount++;
        }

        binding.approvedCount.setText(String.valueOf(approvedCount));
        binding.pendingCount.setText(String.valueOf(pendingCount));
        binding.rejectedCount.setText(String.valueOf(rejectedCount));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Giải phóng tài nguyên
    }
}