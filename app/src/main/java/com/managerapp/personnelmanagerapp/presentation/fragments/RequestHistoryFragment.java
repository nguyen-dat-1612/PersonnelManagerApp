package com.managerapp.personnelmanagerapp.presentation.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

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
import com.managerapp.personnelmanagerapp.presentation.activities.MainActivity;
import com.managerapp.personnelmanagerapp.presentation.adapters.LeaveApplicationAdapter;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.viewmodel.LeaveApplicationViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RequestHistoryFragment extends BaseFragment {

    private static final String TAG = "RequestHistoryFragment";
    private FragmentRequestHistoryBinding binding;
    private List<LeaveApplicationResponse> leaveApplications = new ArrayList<>();

    private LeaveApplicationAdapter adapter;
    private LeaveApplicationViewModel viewModel;
    private long userId;
    public RequestHistoryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRequestHistoryBinding.inflate(inflater, container, false);
        userId = requireActivity().getIntent().getLongExtra("userId", -1);
        if (userId <= 0) {
            Log.e(TAG, "Không nhận được userId từ intent");
        }
        viewModel = new ViewModelProvider(this).get(LeaveApplicationViewModel.class);

        adapter = new LeaveApplicationAdapter(leaveApplications, leaveApplication -> {
            Toast.makeText(requireContext(),leaveApplication.getId() + " ", Toast.LENGTH_LONG ).show();
        });

        loadLeaveApplication();


        binding.swipeRefresh.setOnRefreshListener(() -> {
            loadLeaveApplication();
        });

        binding.fabNewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.request_fragment);
                navController.navigate(R.id.action_requestHistoryFragment_to_newRequestFragment);
            }
        });

        binding.backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        return binding.getRoot();
    }

    public void loadLeaveApplication() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            binding.swipeRefresh.setRefreshing(false);
            if (state instanceof UiState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
            } else if (state instanceof UiState.Success) {
                leaveApplications.clear();
                List<LeaveApplicationResponse> data = ((UiState.Success<List<LeaveApplicationResponse>>) state).getData();
                leaveApplications.addAll(data);

                // Thêm dòng này để cập nhật thống kê
                updateLeaveStats(data);

                if (leaveApplications.isEmpty()) {
                    binding.emptyView.setVisibility(VISIBLE);
                    binding.recyclerViewHistory.setVisibility(GONE);
                } else {
                    binding.recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerViewHistory.setAdapter(adapter);
                    binding.emptyView.setVisibility(GONE);
                    binding.recyclerViewHistory.setVisibility(VISIBLE);
                }
                adapter.notifyDataSetChanged();
            } else if (state instanceof UiState.Error) {
                Toast.makeText(requireContext(), ((UiState.Error) state).getErrorMessage(), Toast.LENGTH_SHORT).show();
                binding.emptyView.setVisibility(VISIBLE);
            }
        });
        viewModel.loadLeaveAppliations((int) userId);
    }

    private void updateLeaveStats(List<LeaveApplicationResponse> applications) {
        int approvedCount = 0;
        int pendingCount = 0;
        int rejectedCount = 0;

        for (LeaveApplicationResponse application : applications) {
            if (application.getFormStatusEnum() != null) {
                switch (application.getFormStatusEnum()) {
                    case "APPROVED":
                        approvedCount++;
                        break;
                    case "PENDING":
                        pendingCount++;
                        break;
                    case "REJECTED":
                        rejectedCount++;
                        break;
                    default:
                        // Không làm gì với các trạng thái khác
                        break;
                }
            }
        }

        // Cập nhật lên giao diện
        binding.approvedCount.setText(String.valueOf(approvedCount));
        binding.pendingCount.setText(String.valueOf(pendingCount));
        binding.rejectedCount.setText(String.valueOf(rejectedCount));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}