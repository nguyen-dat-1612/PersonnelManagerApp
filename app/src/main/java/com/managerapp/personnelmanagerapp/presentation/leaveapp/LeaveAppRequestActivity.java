package com.managerapp.personnelmanagerapp.presentation.leaveapp;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.databinding.ActivityLeaveAppRequestBinding;
import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import java.util.ArrayList;
import java.util.List;

public class LeaveAppRequestActivity extends BaseActivity {

    private final String TAG = "LeaveAppRequestActivityMain";
    private ActivityLeaveAppRequestBinding binding;
    private LeaveRequestAdapter adapter;
    private List<LeaveApplicationResponse> leaveRequests;
    private LeaveRequestViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLeaveAppRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LeaveRequestViewModel.class);
        viewModel.getApplicationIsPending();
        binding.rvLeaveRequests.setLayoutManager(new LinearLayoutManager(this));
        leaveRequests = new ArrayList<>();

        adapter = new LeaveRequestAdapter(
                leaveRequests,
                leaveRequest -> {
                    viewModel.confirmApplication(leaveRequest.getId(), "REJECTED");
                },
                leaveRequest -> {
                    viewModel.confirmApplication(leaveRequest.getId(), "APPROVED");
                },
                this
        );
        binding.rvLeaveRequests.setAdapter(adapter);

        setOnListener();
        loadLeaveRequest();
        observeConfirmResult();
    }

    public void setOnListener() {
        binding.backBtn.setOnClickListener(v -> {
            finish();
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.getApplicationIsPending();
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    public void loadLeaveRequest() {
        viewModel.getUiStateLoad().observe(this, state -> {
            if (state instanceof UiState.Loading) {
            } else if (state instanceof UiState.Success) {
                leaveRequests.clear();
                leaveRequests.addAll(((UiState.Success<List<LeaveApplicationResponse>>) state).getData());
                if (leaveRequests.isEmpty()) {
                    binding.viewEmpty.getRoot().setVisibility(View.VISIBLE);
                } else {
                    binding.viewEmpty.getRoot().setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            } else if (state instanceof UiState.Error) {
                Log.e(TAG, ((UiState.Error) state).getErrorMessage());
            }
        });
    }

    private void observeConfirmResult() {
        viewModel.getUiStateConfirm().observe(this, result -> {
            if (result instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(VISIBLE);
                binding.viewSendSuccess.getRoot().setVisibility(GONE);
            }
            else if (result instanceof UiState.Success) {

                binding.progressOverlay.getRoot().setVisibility(GONE);
                binding.viewSendSuccess.getRoot().setVisibility(View.VISIBLE);
                viewModel.getApplicationIsPending();
                new Handler().postDelayed(() -> {
                    binding.viewSendSuccess.getRoot().setVisibility(View.INVISIBLE);
                }, 3000);
            } else if (result instanceof UiState.Error) {
                Toast.makeText(this, "Đã có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Confirm error: " + ((UiState.Error) result).getErrorMessage());
            }
        });
    }
}