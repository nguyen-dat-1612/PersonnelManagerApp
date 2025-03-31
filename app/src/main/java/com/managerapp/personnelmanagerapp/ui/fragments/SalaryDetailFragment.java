package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentSalaryDetailBinding;


public class SalaryDetailFragment extends BaseFragment {

    private FragmentSalaryDetailBinding binding;
    private String id;
    public SalaryDetailFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSalaryDetailBinding.inflate(inflater, container, false);

        // Lấy contractId từ arguments
        Bundle args = getArguments();
        id = args != null ? args.getString("id", "DEFAULT_ID") : "DEFAULT_ID";
        return binding.getRoot();
    }
}