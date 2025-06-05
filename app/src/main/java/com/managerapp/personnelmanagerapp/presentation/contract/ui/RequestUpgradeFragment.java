package com.managerapp.personnelmanagerapp.presentation.contract.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.JobGradeResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentRequestUpgradeBinding;
import com.managerapp.personnelmanagerapp.domain.model.JobGrade;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.presentation.contract.state.RequestUpgradeUiState;
import com.managerapp.personnelmanagerapp.presentation.contract.viewmodel.RequestUpgradeViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RequestUpgradeFragment extends Fragment {
    private FragmentRequestUpgradeBinding binding;
    private RequestUpgradeViewModel viewModel;
    private NavController navController;
    private String selectedId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RequestUpgradeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRequestUpgradeBinding.inflate(inflater, container, false);
        observeData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
    }

    private void setupUI(UserProfileResponse user, List<JobGradeResponse> jobGrades, JobGradeResponse currentJobGrade) {
        // Gán dữ liệu cho TextView
        binding.nameText.setText(user.getFullName());
        binding.positionNameText.setText(user.getPositionName());
        binding.departmentNameText.setText(user.getDepartmentName());
        binding.jobGradeText.setText(currentJobGrade.getName() + " (" + currentJobGrade.getCoefficient() + ")");

        // Tạo danh sách hiển thị
        List<String> displayList = jobGrades.stream()
                .map(j -> j.getName() + " (" + j.getCoefficient() + ")")
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.spinner_item,
                displayList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerReasons.setAdapter(adapter);

        // Gắn listener để lấy id
        binding.spinnerReasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                JobGradeResponse selectedGrade = jobGrades.get(position);
                selectedId = selectedGrade.getId();
                Log.d("SelectedJobGrade", "ID: " + selectedId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.btnSend.setOnClickListener(v -> {
            String reason = binding.edtReason.getText().toString();

            if (reason.isEmpty()) {
                binding.edtReason.setError("Vui lòng nhập lý do");
                return;
            }

            viewModel.createSalaryPromotion(reason,currentJobGrade.getId(), selectedId,user.getId());
        });

        // Nút back
        binding.backBtn.setOnClickListener(v ->{
            navController.popBackStack();
        });

        // Nút help
        binding.helpBtn.setOnClickListener(v -> {
            // TODO: Mở dialog hoặc hướng dẫn
        });
    }

    private void observeData() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {

            if (state instanceof RequestUpgradeUiState.Loading) {
                binding.progressOverlayWhite.getRoot().setVisibility(VISIBLE);
            } else if (state instanceof RequestUpgradeUiState.DataLoaded) {
                RequestUpgradeUiState.DataLoaded dataLoadedState = (RequestUpgradeUiState.DataLoaded) state;
                List<JobGradeResponse> jobGrades = dataLoadedState.jobGrades();
                UserProfileResponse user = dataLoadedState.getUser();
                JobGradeResponse currentJobGrade = dataLoadedState.getCurrentJobGrade();
                setupUI(user,jobGrades,currentJobGrade);
                binding.progressOverlayWhite.getRoot().setVisibility(GONE);
            } else if (state instanceof RequestUpgradeUiState.Error) {
                binding.progressOverlayWhite.getRoot().setVisibility(GONE);
                binding.viewNoInternet.getRoot().setVisibility(VISIBLE);
                RequestUpgradeUiState.Error errorState = (RequestUpgradeUiState.Error) state;
                Log.e("RequestUpgradeFragment", "Error: " + errorState.message);
            }
        });

        viewModel.getCreateSalaryProUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(VISIBLE);
            } else if (state instanceof UiState.Success) {
                binding.progressOverlay.getRoot().setVisibility(GONE);
                Toast.makeText(requireContext(), "Gửi yêu cầu nâng hệ số lương thành công", Toast.LENGTH_SHORT).show();
                navController.popBackStack();
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(GONE);
                UiState.Error errorState = (UiState.Error) state;
                Toast.makeText(requireContext(), errorState.getErrorMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RequestUpgradeFragment", "Error: " + errorState.getErrorMessage());
            }
        });
    }
}