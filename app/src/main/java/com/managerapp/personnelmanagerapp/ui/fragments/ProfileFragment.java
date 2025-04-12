package com.managerapp.personnelmanagerapp.ui.fragments;

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
import com.managerapp.personnelmanagerapp.ui.activities.MainActivity;
import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentProfileBinding;
import com.managerapp.personnelmanagerapp.ui.activities.ChangePasswordActivity;
import com.managerapp.personnelmanagerapp.ui.activities.LoginActivity;
import com.managerapp.personnelmanagerapp.ui.activities.ProfileInfoActivity;
import com.managerapp.personnelmanagerapp.ui.activities.SettingsActivity;
import com.managerapp.personnelmanagerapp.ui.state.MainState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.MainViewModel;


public class ProfileFragment extends BaseFragment {

    private FragmentProfileBinding binding;
    private MainViewModel mainViewModel;
    private final String TAG = "ProfileFragment";
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        loadUserInfo();

        binding.btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        binding.btnProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ProfileInfoActivity.class);
                startActivity(intent);
            }
        });

        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        binding.btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });


        return binding.getRoot();
    }
    private void loadUserInfo() {
        User user = ((MainActivity) requireActivity()).getCurrentUser();
        if (user == null) {
            mainViewModel.loadUser(); // Giả sử method này trong ViewModel

            mainViewModel.getMainState().observe(getViewLifecycleOwner(), state -> {

                if (state instanceof MainState.Loading) {

                }
                else if (state instanceof MainState.Success) {
                    User newUser = ((MainState.Success) state).getUser();
                    ((MainActivity) requireActivity()).setUser(newUser);

                    Glide.with(requireContext())
                            .load(newUser.getAvatar())
                            .placeholder(R.drawable.ic_default_avatar)
                            .error(R.drawable.ic_broken_image)
                            .into(binding.imageUser);

                    binding.main.setVisibility(View.VISIBLE);
                }
                else if (state instanceof MainState.Error) {
                    String errorMsg = ((MainState.Error) state).getMessage();
                    Log.e(TAG, "Error loading profile: " + errorMsg);
                }
            });
        } else {
            Glide.with(requireContext())
                    .load(user.getAvatar())
                    .placeholder(R.drawable.ic_default_avatar) // Ảnh mặc định khi đang tải
                    .error(R.drawable.ic_broken_image)
                    .into(binding.imageUser);
            binding.userNameText.setText(user.getFullName());
        }

    }
}