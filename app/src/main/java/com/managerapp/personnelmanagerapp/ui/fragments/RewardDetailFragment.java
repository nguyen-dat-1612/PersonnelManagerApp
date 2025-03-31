package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.managerapp.personnelmanagerapp.databinding.FragmentRewardDetailBinding;

public class RewardDetailFragment extends Fragment {

    private FragmentRewardDetailBinding binding;
    private int rewardId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Sử dụng View Binding
        binding = FragmentRewardDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Nhận dữ liệu bằng Safe Args
        RewardDetailFragmentArgs args = RewardDetailFragmentArgs.fromBundle(getArguments());
        rewardId = args.getRewardId();

        // Hiển thị dữ liệu
        binding.textView.setText("Reward ID: " + rewardId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Tránh memory leak
    }
}
