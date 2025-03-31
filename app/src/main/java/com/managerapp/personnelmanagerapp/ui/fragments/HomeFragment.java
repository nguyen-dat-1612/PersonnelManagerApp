package com.managerapp.personnelmanagerapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.domain.model.User;
import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentHomeBinding;
import com.managerapp.personnelmanagerapp.ui.activities.ContractActivity;
import com.managerapp.personnelmanagerapp.ui.activities.FeedBackActivity;
import com.managerapp.personnelmanagerapp.ui.activities.LoginActivity;
import com.managerapp.personnelmanagerapp.ui.activities.RequestActivity;
import com.managerapp.personnelmanagerapp.ui.activities.RewardDisciplineActivity;
import com.managerapp.personnelmanagerapp.ui.activities.SalaryActivity;
import com.managerapp.personnelmanagerapp.ui.state.HomeState;
import com.managerapp.personnelmanagerapp.ui.state.ProfileInfoState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.HomeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
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

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        loadUserInfo();
        binding.logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            requireActivity().finish(); // Đảm bảo Activity hiện tại cũng bị đóng
            startActivity(intent);
        });

        // Request Button - Chỉ mở RequestActivity bình thường
        binding.YeucauBtn.setOnClickListener(v -> {
            if (getView() != null && getActivity() != null) {
                Intent intent = new Intent(requireActivity(), RequestActivity.class);
                startActivity(intent);
            }
        });

        // Feedback Button
        binding.btnFeedBack.setOnClickListener(v -> {
            if (getView() != null && getActivity() != null) {
                Intent intent = new Intent(requireContext(), FeedBackActivity.class);
                startActivity(intent);
            }
        });

        // Contract Button
        binding.btnContracts.setOnClickListener(v -> {
            if (getView() != null && getActivity() != null) {
                Intent intent = new Intent(requireContext(), ContractActivity.class);
                startActivity(intent);
            }
        });
        // Reward And Discipline Button
        binding.btnRewardAndDiscipline.setOnClickListener(v -> {
            if (getView() != null && getActivity() != null) {
                Intent intent = new Intent(requireContext(), RewardDisciplineActivity.class);
                startActivity(intent);
            }
        });
        // Salary Button
        binding.btnSalary.setOnClickListener(v -> {
            if (getView() != null && getActivity() != null) {
                Intent intent = new Intent(requireContext(), SalaryActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
    private void loadUserInfo() {
        viewModel.loadUser(); // Giả sử method này trong ViewModel

        viewModel.getHomeState().observe(getViewLifecycleOwner(), state -> {

            if (state instanceof HomeState.Loading) {
            }
            else if (state instanceof HomeState.Success) {
                User user = ((HomeState.Success) state).getUser();
                binding.textWelcome.setText("Xin chào, \n" + user.getFullName());
            }
            else if (state instanceof HomeState.Error) {
                String errorMsg = ((HomeState.Error) state).getMessage();
                Log.e(TAG, "Error loading profile: " + errorMsg);
            }
        });
    }


    @Override
    public void onDestroyView() {
        // Clear all click listeners
        binding.logoutBtn.setOnClickListener(null);
        binding.YeucauBtn.setOnClickListener(null);
        binding.btnFeedBack.setOnClickListener(null);
        binding.btnContracts.setOnClickListener(null);
        binding.btnRewardAndDiscipline.setOnClickListener(null);
        binding.btnSalary.setOnClickListener(null);

        binding = null;
        super.onDestroyView();
    }
    private static String TAG = "HomeFragment";
}