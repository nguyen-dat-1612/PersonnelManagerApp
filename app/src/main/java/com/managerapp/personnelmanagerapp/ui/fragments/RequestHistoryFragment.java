package com.managerapp.personnelmanagerapp.ui.fragments;

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
import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentRequestHistoryBinding;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.ui.activities.MainActivity;
import com.managerapp.personnelmanagerapp.ui.adapters.LeaveApplicationAdapter;
import com.managerapp.personnelmanagerapp.ui.state.LeaveApplicationState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.LeaveApplicationViewModel;

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
        viewModel.getLeaveApplicationState().observe(getViewLifecycleOwner(), state -> {
            binding.swipeRefresh.setRefreshing(false);
            if (state instanceof LeaveApplicationState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
            } else if (state instanceof LeaveApplicationState.Success) {
                leaveApplications.clear();
                leaveApplications.addAll(((LeaveApplicationState.Success) state).leaveApplications);
                binding.recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewHistory.setAdapter(adapter);
                binding.emptyView.setVisibility(GONE);
                binding.recyclerViewHistory.setVisibility(VISIBLE);
            } else if (state instanceof LeaveApplicationState.Error) {
                Toast.makeText(requireContext(), ((LeaveApplicationState.Error) state).message, Toast.LENGTH_SHORT).show();
                binding.emptyView.setVisibility(VISIBLE);
            } else if (state instanceof LeaveApplicationState.Empty) {
                binding.emptyView.setVisibility(VISIBLE);
                binding.recyclerViewHistory.setVisibility(GONE);
            }
        });
        adapter.notifyDataSetChanged();
        viewModel.loadLeaveAppliations((int) userId);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}