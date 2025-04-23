package com.managerapp.personnelmanagerapp.presentation.main;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.presentation.worklog.WorkLogActivity;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.LeaveAppRequestActivity;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.LeaveApplicationActivity;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentHomeBinding;
import com.managerapp.personnelmanagerapp.presentation.contract.ContractActivity;
import com.managerapp.personnelmanagerapp.presentation.feedback.FeedBackActivity;
import com.managerapp.personnelmanagerapp.presentation.login.LoginActivity;
import com.managerapp.personnelmanagerapp.presentation.decision.DecisionActivity;
import com.managerapp.personnelmanagerapp.presentation.salary.SalaryActivity;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.utils.Role;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    private MainViewModel mainViewModel;
    private FragmentHomeBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.loadUserAndRole();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        loadUserInfo();
        setupListeners();
        return binding.getRoot();
    }

    private void setupListeners() {
        binding.logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        binding.YeucauBtn.setOnClickListener(v -> startActivity(new Intent(requireContext(), LeaveApplicationActivity.class)));
        binding.btnFeedBack.setOnClickListener(v -> startActivity(new Intent(requireContext(), FeedBackActivity.class)));
        binding.btnContracts.setOnClickListener(v -> startActivity(new Intent(requireContext(), ContractActivity.class)));
        binding.btnRewardAndDiscipline.setOnClickListener(v -> startActivity(new Intent(requireContext(), DecisionActivity.class)));
        binding.btnSalary.setOnClickListener(v -> startActivity(new Intent(requireContext(), SalaryActivity.class)));
        binding.btnCongTac.setOnClickListener(v -> startActivity(new Intent(requireContext(), WorkLogActivity.class)));
        binding.DuyetCardView.setOnClickListener(v -> startActivity(new Intent(requireContext(), LeaveAppRequestActivity.class)));
    }


    private void loadUserInfo() {
        UserProfileResponse user = ((MainActivity) requireActivity()).getCurrentUser();
        if (user != null) {
            updateUI(user);
        } else {
            mainViewModel.loadUserAndRole();
            mainViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
                if (state instanceof UiState.Success) {
                    UserProfileResponse newUser = ((UiState.Success<UserProfileResponse>) state).getData();
                    ((MainActivity) requireActivity()).setUser(newUser);
                    updateUI(newUser);
                } else if (state instanceof UiState.Error) {
                    Log.e(TAG, "Error loading profile: " + ((UiState.Error) state).getErrorMessage());
                }
            });
        }
    }

    private void updateUI(UserProfileResponse user) {
        binding.numRewardText.setText(String.valueOf(user.getNumReward()));
        binding.numDisciplineText.setText(String.valueOf(user.getNumDiscipline()));
        binding.positionNameText.setText("Chức vụ: " + user.getPositionName());
        binding.departmentNameText.setText("Nơi làm việc : " + user.getDepartmentName());
        binding.serviceDurationText.setText(String.valueOf(user.getServiceDuration()));
        loadAvatar(user);

        String roleStr = mainViewModel.getRole(); // VD: "MANAGER"
        Role role = Role.fromString(roleStr);

        if (Role.MANAGER.equals(role)) {
            binding.DuyetCardView.setVisibility(VISIBLE);
        } else {
            binding.DuyetCardView.setVisibility(GONE);
        }
    }
    private void loadAvatar(UserProfileResponse user) {
        Log.d("AVATAR_URL", "Avatar URL:" + user.getAvatar() +".end");

        Glide.with(requireContext())
                .load(user.getAvatar())
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_broken_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Log lỗi chi tiết
                        if (e != null) {
                            Log.e("Glide", "Error loading image: " + e.getMessage());
                        }
                        return false;  // Cho phép Glide xử lý lỗi mặc định
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;  // Không cần can thiệp thêm
                    }
                })
                .into(binding.imageUser);
    }

    @Override
    public void onDestroyView() {
        binding.logoutBtn.setOnClickListener(null);
        binding.YeucauBtn.setOnClickListener(null);
        binding.btnFeedBack.setOnClickListener(null);
        binding.btnContracts.setOnClickListener(null);
        binding.btnRewardAndDiscipline.setOnClickListener(null);
        binding.btnSalary.setOnClickListener(null);
        binding.cardViewCollaborate.setOnClickListener(null);

        binding = null;
        super.onDestroyView();
    }
}
