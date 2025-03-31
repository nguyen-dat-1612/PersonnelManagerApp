package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import com.managerapp.personnelmanagerapp.databinding.FragmentRewardListBinding;
import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;

public class RewardListFragment extends BaseFragment {

    private FragmentRewardListBinding binding;

    public RewardListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRewardListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (binding.emptyAnimation == null) {
            Log.e("RewardListFragment", "emptyAnimation is null. Check layout!");
            return;
        }

        binding.emptyAnimation.setOnClickListener(v -> {
            int rewardId = 123; // ID thực tế lấy từ danh sách

            Navigation.findNavController(view).navigate(
                    RewardListFragmentDirections.actionRewardListFragmentToRewardDetailFragment(rewardId)
            );
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Tránh memory leak
    }
}
