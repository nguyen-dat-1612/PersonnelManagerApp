package com.managerapp.personnelmanagerapp.presentation.home;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.domain.model.FeatureItem;
import com.managerapp.personnelmanagerapp.presentation.main.viewmodel.MainViewModel;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentHomeBinding;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.domain.model.Role;
import com.managerapp.personnelmanagerapp.utils.ImageLoaderUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    private MainViewModel mainViewModel;
    private FragmentHomeBinding binding;
    private NavController navController;
    private Role role;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        loadUserInfo();
        setupListeners();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
    }

    private void setupListeners() {
        binding.logoutBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_loginFragment);
        });

        binding.YeucauBtn.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_requestHistoryFragment)
        );
        binding.btnFeedBack.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_feedBackFragment)
        );

        binding.btnContracts.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_contractListFragment)
        );

        binding.btnRewardAndDiscipline.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_decisionListFragment)
        );

        binding.btnSendNotification.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_sendNotificationFragment)
        );

        binding.btnCongTac.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_workLogFragment);
        });
        binding.DuyetCardView.setOnClickListener(v -> {
            if (Role.STAFF.equals(role)) {
                navController.navigate(R.id.action_mainFragment_to_createDecisionFragment);
            } else {
                navController.navigate(R.id.action_mainFragment_to_leaveAppRequestFragment);
            }
        });
        binding.reportBtn.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_listReportFragment)
        );

        binding.btnDuyet.setOnClickListener(v -> {
            // Den trang duyet quyet dinh

            navController.navigate(R.id.action_mainFragment_to_approveDecisionFragment);
        });

        binding.btnTaoQuyetDinh.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_createDecisionFragment);
        });

        binding.btnSendNotification2.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_sendNotificationFragment);
        });

        binding.btnContracts2.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_contractListFragment);
        });

        binding.btnTangLuong.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_salaryPromotionApproveFragment);
        });
    }


    private void loadUserInfo() {
        mainViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Success) {

                UserProfileResponse newUser = ((UiState.Success<UserProfileResponse>) state).getData();
                if (newUser != null) {
                    updateUI(newUser);
                }
            } else if (state instanceof UiState.Error) {
                Log.e(TAG, "Error loading profile: " + ((UiState.Error) state).getErrorMessage());
            }
        });

        mainViewModel.getRoleUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Success) {
                String roleStr = ((UiState.Success<String>) state).getData();
                role = Role.fromString(roleStr);
                binding.gridlayoutAdmin.setVisibility(GONE);
                if (Role.USER.equals(role)) {
                    binding.gridlayout.setVisibility(VISIBLE);
                    binding.DuyetCardView.setVisibility(GONE);
                    binding.reportBtn.setVisibility(GONE);
                    binding.btnSendNotification.setVisibility(GONE);
                } else if (Role.MANAGER.equals(role)){
                    binding.gridlayout.setVisibility(VISIBLE);
                    binding.DuyetCardView.setVisibility(VISIBLE);
                    binding.reportBtn.setVisibility(GONE);
                } else if (Role.STAFF.equals(role)) {
                    binding.gridlayout.setVisibility(VISIBLE);

                    binding.DuyetCardView.setVisibility(VISIBLE);
                    binding.textDuyet.setText("Tạo quyết định");
                    binding.reportBtn.setVisibility(VISIBLE);
                    binding.textSendNotification.setText("Gửi thông báo");
                } else if (Role.ADMIN.equals(role)) {
                    binding.gridlayout.setVisibility(GONE);
                    binding.gridlayoutAdmin.setVisibility(VISIBLE);
                }
            }
        });
    }

    private void updateUI(UserProfileResponse user) {
        binding.numRewardText.setText(String.valueOf(user.getNumReward()));
        binding.numDisciplineText.setText(String.valueOf(user.getNumDiscipline()));
        binding.positionNameText.setText("Chức vụ: " + user.getPositionName());
        binding.departmentNameText.setText("Nơi làm việc : " + user.getDepartmentName());
        binding.serviceDurationText.setText(String.valueOf(user.getServiceDuration()));
        ImageLoaderUtils.loadAvatar(requireContext(), user.getAvatar(), binding.imageUser);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

}
