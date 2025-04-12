package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.managerapp.personnelmanagerapp.databinding.FragmentRewardListBinding;
import com.managerapp.personnelmanagerapp.ui.activities.RewardDisciplineActivity;
import com.managerapp.personnelmanagerapp.ui.adapters.RewardAdapter;
import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.ui.state.ContractListState;
import com.managerapp.personnelmanagerapp.ui.state.RewardState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.RewardViewModel;

import java.util.ArrayList;

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
        rewardViewModel.getRewardState().observe(getViewLifecycleOwner(), rewardState -> {
            if (rewardState instanceof RewardState.Loading) {
                binding.swipeRefreshLayout.setRefreshing(true);
            } else if(rewardState instanceof  RewardState.ListSuccess) {
                binding.recyclerViewRewards.setLayoutManager(new LinearLayoutManager(requireContext()));
                adapter = new RewardAdapter(((RewardState.ListSuccess) rewardState).getData());
                binding.recyclerViewRewards.setAdapter(adapter);
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.recyclerViewRewards.setVisibility(View.VISIBLE);
                binding.emptyView.setVisibility(View.GONE);
            } else if(rewardState instanceof  RewardState.Error) {
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
