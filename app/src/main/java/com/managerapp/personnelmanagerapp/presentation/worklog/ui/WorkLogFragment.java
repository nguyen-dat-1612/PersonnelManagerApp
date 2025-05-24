package com.managerapp.personnelmanagerapp.presentation.worklog.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentWorkLogBinding;
import com.managerapp.personnelmanagerapp.presentation.worklog.viewmodel.WorkLogViewModel;
import com.managerapp.personnelmanagerapp.presentation.worklog.adapter.WorkLogAdapter;

import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import com.managerapp.personnelmanagerapp.R;
@AndroidEntryPoint
public class WorkLogFragment extends Fragment {
    private WorkLogViewModel viewModel;
    private WorkLogAdapter adapter;
    private FragmentWorkLogBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WorkLogViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkLogBinding.inflate(inflater, container, false);
        setupRecyclerView();
        observeWorkLogs();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onListener();
    }
    public void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
    public void onListener() {
        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_workLogFragment_to_mainFragment);
            navController.popBackStack();
        });
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshData();
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    public void observeWorkLogs() {
        binding.errorView.getRoot().setVisibility(View.GONE);
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            binding.progressOverlay.getRoot().setVisibility(
                    state.isLoading() ? View.VISIBLE : View.GONE
            );
            if (state.getWorkLogs() != null) {
                List<WorkLogResponse> workLogs = state.getWorkLogs();
                Collections.reverse(workLogs);
                adapter = new WorkLogAdapter(workLogs);
                binding.recyclerView.setAdapter(adapter);
            }
            if (state.getErrorMessage() != null) {
                binding.errorView.getRoot().setVisibility(View.VISIBLE);
            }
        });
    }

}