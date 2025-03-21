package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentRewardBinding;


public class RewardFragment extends Fragment {

    private FragmentRewardBinding binding;

    public RewardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRewardBinding.inflate(inflater, container, false);

        binding.emptyView.setVisibility(View.VISIBLE);

        return binding.getRoot();
    }
}