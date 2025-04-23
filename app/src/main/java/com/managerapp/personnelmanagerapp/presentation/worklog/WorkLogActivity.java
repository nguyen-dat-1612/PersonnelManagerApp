package com.managerapp.personnelmanagerapp.presentation.worklog;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.databinding.ActivityWorkLogBinding;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WorkLogActivity extends AppCompatActivity {

    private static final String TAG = "WorkLogActivity";

    private WorkLogViewModel viewModel;

    private WorkLogAdapter adapter;

    private ActivityWorkLogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityWorkLogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(WorkLogViewModel.class);
        viewModel.getWorkLogs();
        loadWorkLog();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.backBtn.setOnClickListener(v -> {
            finish();
        });
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.getWorkLogs();
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    public void loadWorkLog() {
        binding.errorView.getRoot().setVisibility(GONE);
        viewModel.getUiState().observe(this, state -> {
            if (state instanceof  UiState.Loading) {
            }
            else if (state instanceof UiState.Success) {
                List<WorkLogResponse> workLogs = ((UiState.Success<List<WorkLogResponse>>) state).getData();
                adapter = new WorkLogAdapter(workLogs);
                binding.recyclerView.setAdapter(adapter);
            } else if (state instanceof UiState.Error) {
                binding.errorView.getRoot().setVisibility(VISIBLE);
                String errorMessage = ((UiState.Error) state).getErrorMessage();
                Log.e(TAG, "Lấy dữ liệu thất bại: " + errorMessage);
            }
        });
    }

}