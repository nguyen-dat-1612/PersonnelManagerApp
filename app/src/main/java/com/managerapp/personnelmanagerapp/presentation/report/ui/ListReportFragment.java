package com.managerapp.personnelmanagerapp.presentation.report.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentListReportBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListReportFragment extends Fragment {

    private FragmentListReportBinding binding;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListReportBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController =  Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        setupListeners();

    }


    private void setupListeners() {
        binding.backBtn.setOnClickListener(v -> {
            navController.popBackStack();
        });

        binding.salaryReport.getRoot().setOnClickListener(v -> {
            navController.navigate(R.id.action_listReportFragment_to_payrollMonthlyFragment);
        });

        binding.contractReport.getRoot().setOnClickListener(v -> {
            navController.navigate(R.id.action_listReportFragment_to_contractExpireFragment);
        });
    }

    @Override
    public void onDestroyView() {
        binding.backBtn.setOnClickListener(null);
        binding = null;
        super.onDestroyView();
    }
}
