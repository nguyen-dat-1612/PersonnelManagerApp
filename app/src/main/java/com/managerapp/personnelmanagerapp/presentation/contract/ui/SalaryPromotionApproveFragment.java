package com.managerapp.personnelmanagerapp.presentation.contract.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.DialogSalaryPromotionDetailBinding;
import com.managerapp.personnelmanagerapp.databinding.FragmentSalaryPromotionApproveBinding;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.presentation.contract.adapter.SalaryPromotionAdapter;
import com.managerapp.personnelmanagerapp.presentation.contract.viewmodel.ApproveSalaryViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SalaryPromotionApproveFragment extends Fragment {
    private FragmentSalaryPromotionApproveBinding binding;
    private ApproveSalaryViewModel viewModel;
    private SalaryPromotionAdapter adapter;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ApproveSalaryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSalaryPromotionApproveBinding.inflate(inflater, container, false);
        setUpUI();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeView();
        onListener();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
    }

    public void setUpUI() {
        adapter = new SalaryPromotionAdapter(promotion -> {
            showPromotionDetailDialog(requireContext(), promotion);
        });
        binding.recyclerViewSalary.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewSalary.setAdapter(adapter);

    }

    public void onListener() {
        binding.backBtn.setOnClickListener(v -> navController.popBackStack());

        binding.btnHistory.setOnClickListener(v -> {
            navController.navigate(R.id.action_salaryPromotionApproveFragment_to_historyApproveFragment);
        });
    }

    private void showPromotionDetailDialog(Context context, SalaryPromotion promotion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        LayoutInflater inflater = LayoutInflater.from(context);

        DialogSalaryPromotionDetailBinding dialogBinding = DialogSalaryPromotionDetailBinding.inflate(inflater);
        builder.setView(dialogBinding.getRoot());

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        dialogBinding.tvUser.setText(getString(R.string.label_user, promotion.getUserName()));
        dialogBinding.tvCurrentGrade.setText(getString(R.string.label_current_grade, promotion.getCurrentJobGradeName()));
        dialogBinding.tvRequestGrade.setText(getString(
                R.string.label_request_grade,
                promotion.getRequestJobGradeName(),
                (int) promotion.getRequestJobGradeValue())
        );
        dialogBinding.tvReason.setText(getString(R.string.label_reason, promotion.getReason()));

        dialogBinding.edtNote.requestFocus();

        dialogBinding.btnApprove.setOnClickListener(v -> {
            String note = dialogBinding.edtNote.getText().toString().trim();
            if (note.isEmpty()) {
                dialogBinding.edtNote.setError(getString(R.string.error_note_required_approve));
                return;
            }
            viewModel.approveSalaryPromotion((int) promotion.getId(), FormStatusEnum.APPROVED, note);
            dialog.dismiss();
        });

        dialogBinding.btnReject.setOnClickListener(v -> {
            String note = dialogBinding.edtNote.getText().toString().trim();
            if (note.isEmpty()) {
                dialogBinding.edtNote.setError(getString(R.string.error_note_required_reject));
                return;
            }
            viewModel.approveSalaryPromotion((int) promotion.getId(), FormStatusEnum.REJECTED, note);
            dialog.dismiss();
        });

        dialog.show();
    }


    private void observeView() {
        viewModel.getSalaryPromotionUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                List<SalaryPromotion> promotions = ((UiState.Success<List<SalaryPromotion>>) state).getData();
                if (promotions.isEmpty()) {
                    binding.emptyView.getRoot().setVisibility(View.VISIBLE);
                } else {
                    binding.emptyView.getRoot().setVisibility(View.GONE);
                }
                adapter.submitList(promotions);
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                binding.errorView.getRoot().setVisibility(View.VISIBLE);
                String errorMessage = ((UiState.Error) state).getErrorMessage();
                showToast(errorMessage);
            }
        });

        viewModel.getUpdateSalaryPromotionUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                SalaryPromotion promotion = ((UiState.Success<SalaryPromotion>) state).getData();
                if (promotion.getStatus().equals(FormStatusEnum.APPROVED.toString())) {
                    showToast(getString(R.string.toast_approve_success));
                } else {
                    showToast(getString(R.string.toast_reject_success));
                }
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                String errorMessage = ((UiState.Error) state).getErrorMessage();
                showToast(errorMessage);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}