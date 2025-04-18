package com.managerapp.personnelmanagerapp.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.domain.model.User;
import com.managerapp.personnelmanagerapp.presentation.activities.CollaborateActivity;
import com.managerapp.personnelmanagerapp.presentation.activities.LeaveApplicationActivity;
import com.managerapp.personnelmanagerapp.presentation.activities.MainActivity;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentHomeBinding;
import com.managerapp.personnelmanagerapp.presentation.activities.ContractActivity;
import com.managerapp.personnelmanagerapp.presentation.activities.FeedBackActivity;
import com.managerapp.personnelmanagerapp.presentation.activities.LoginActivity;
import com.managerapp.personnelmanagerapp.presentation.activities.RewardDisciplineActivity;
import com.managerapp.personnelmanagerapp.presentation.activities.SalaryActivity;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.viewmodel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment {

    private MainViewModel mainViewModel;
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

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
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
                Intent intent = new Intent(requireActivity(), LeaveApplicationActivity.class);
                User currentUser = ((MainActivity) requireActivity()).getCurrentUser();
                intent.putExtra("userId", currentUser.getId());
                startActivity(intent);
            }
        });

        // Feedback Button
        binding.btnFeedBack.setOnClickListener(v -> {
            if (getView() != null && getActivity() != null) {
                Intent intent = new Intent(requireContext(), FeedBackActivity.class);
                User currentUser = ((MainActivity) requireActivity()).getCurrentUser();
                intent.putExtra("userId", currentUser.getId());
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
                User currentUser = ((MainActivity) requireActivity()).getCurrentUser();
                intent.putExtra("userId", currentUser.getId()); // Dùng lại userId đã kiểm tra
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

        binding.cardViewCollaborate.setOnClickListener(v -> {
            if (getView() != null && getActivity() != null) {
                Intent intent = new Intent(requireActivity(), CollaborateActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }

    private void loadUserInfo() {
        User user = ((MainActivity) requireActivity()).getCurrentUser();
        if (user == null) {
            mainViewModel.loadUser(); // Giả sử method này trong ViewModel

            mainViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {

                if (state instanceof UiState.Loading) {

                }
                else if (state instanceof UiState.Success) {
                    User newUser = ((UiState.Success<User>) state).getData();
                    ((MainActivity) requireActivity()).setUser(newUser);

                    Glide.with(requireContext())
                            .load(newUser.getAvatar())
                            .placeholder(R.drawable.ic_default_avatar)
                            .error(R.drawable.ic_broken_image)
                            .into(binding.imageUser);
                }
                else if (state instanceof UiState.Error) {
                    String errorMsg = ((UiState.Error) state).getErrorMessage();
                    Log.e(TAG, "Error loading profile: " + errorMsg);
                }
            });
        } else {
            Glide.with(requireContext())
                    .load(user.getAvatar())
                    .placeholder(R.drawable.ic_default_avatar) // Ảnh mặc định khi đang tải
                    .error(R.drawable.ic_broken_image)
                    .into(binding.imageUser);
        }

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