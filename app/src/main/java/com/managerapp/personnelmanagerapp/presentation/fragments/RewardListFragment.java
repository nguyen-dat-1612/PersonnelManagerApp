package com.managerapp.personnelmanagerapp.presentation.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentRewardListBinding;
import com.managerapp.personnelmanagerapp.presentation.adapters.RewardAdapter;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.viewmodel.RewardViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RewardListFragment extends BaseFragment {

    private FragmentRewardListBinding binding;
    private RewardViewModel rewardViewModel;
    private RewardAdapter adapter;
    private final String TAG = "RewardListFragment";
    private long userId;
    public RewardListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRewardListBinding.inflate(inflater, container, false);

        userId = requireActivity().getIntent().getLongExtra("userId", -1);
        if (userId <= 0) {
            Log.e(TAG, "Không nhận được userId từ intent");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rewardViewModel = new ViewModelProvider(this).get(RewardViewModel.class);

        loadReward();

        // Pull to refresh
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            loadReward();
        });
    }

    public void loadReward() {
        rewardViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.swipeRefreshLayout.setRefreshing(true);
            } else if(state instanceof UiState.Success) {
                binding.recyclerViewRewards.setLayoutManager(new LinearLayoutManager(requireContext()));
                adapter = new RewardAdapter(((UiState.Success<List<AssignmentResponse>>) state).getData());
                binding.recyclerViewRewards.setAdapter(adapter);
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.recyclerViewRewards.setVisibility(View.VISIBLE);
                binding.emptyView.setVisibility(View.GONE);
            } else if(state instanceof  UiState.Error) {
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.recyclerViewRewards.setVisibility(View.GONE);
                binding.emptyView.setVisibility(View.VISIBLE);
            }
        });
        rewardViewModel.loadAllRewards((int) userId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Tránh memory leak
    }
}
