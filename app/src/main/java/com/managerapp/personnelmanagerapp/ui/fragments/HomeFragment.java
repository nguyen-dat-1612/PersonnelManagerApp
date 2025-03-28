package com.managerapp.personnelmanagerapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentHomeBinding;
import com.managerapp.personnelmanagerapp.ui.activities.ContractActivity;
import com.managerapp.personnelmanagerapp.ui.activities.FeedBackActivity;
import com.managerapp.personnelmanagerapp.ui.activities.LoginActivity;
import com.managerapp.personnelmanagerapp.ui.activities.RequestActivity;
import com.managerapp.personnelmanagerapp.ui.activities.RewardDisciplineActivity;
import com.managerapp.personnelmanagerapp.ui.activities.SalaryActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Logout Button
        binding.logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Request Button - Chỉ mở RequestActivity bình thường
        binding.YeucauBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), RequestActivity.class);
            startActivity(intent);
        });

        // Feedback Button
        binding.btnFeedBack.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), FeedBackActivity.class);
            startActivity(intent);
        });

        // Contract Button
        binding.btnContracts.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ContractActivity.class);
            startActivity(intent);
        });
        // Reward And Discipline Button
        binding.btnRewardAndDiscipline.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), RewardDisciplineActivity.class);
            startActivity(intent);
        });
        // Salary Button
        binding.btnSalary.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SalaryActivity.class);
            startActivity(intent);
        });




        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Giải phóng binding để tránh memory leak
    }
}