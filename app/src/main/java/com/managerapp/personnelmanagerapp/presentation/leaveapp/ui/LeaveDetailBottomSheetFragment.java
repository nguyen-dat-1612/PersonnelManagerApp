package com.managerapp.personnelmanagerapp.presentation.leaveapp.ui;

import static android.view.View.GONE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentLeaveDetailBottomSheetBinding;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.viewmodel.LeaveRequestViewModel;
import com.managerapp.personnelmanagerapp.utils.DateUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LeaveDetailBottomSheetFragment extends BottomSheetDialogFragment {

    private FragmentLeaveDetailBottomSheetBinding binding;
    private LeaveRequestViewModel viewModel;
    private LeaveApplication leaveRequest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            leaveRequest = (LeaveApplication) args.getSerializable("leaveRequest");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLeaveDetailBottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean showApprove = getArguments() != null && getArguments().getBoolean("SHOW_APPROVE", false);

        if (leaveRequest != null) {
            binding.tvName.setText(leaveRequest.getUser().getFullName());
            binding.tvLeaveType.setText(leaveRequest.getLeaveTypeName());
            binding.tvDate.setText(DateUtils.formatDateToVietnamese(leaveRequest.getStartDate())
                    + " - " + DateUtils.formatDateToVietnamese(leaveRequest.getEndDate()));
            binding.tvReason.setText(leaveRequest.getReason());
            binding.tvStatus.setText(leaveRequest.getFormStatusEnum().getLocalizedStringRes());

            int color;
            if (leaveRequest.getFormStatusEnum().equals(FormStatusEnum.PENDING)) {
                color = binding.getRoot().getContext().getColor(R.color.orange);
            } else if (leaveRequest.getFormStatusEnum().equals(FormStatusEnum.REJECTED)) {
                color = binding.getRoot().getContext().getColor(R.color.red);
            } else {
                color = binding.getRoot().getContext().getColor(R.color.green);
            }

            binding.tvStatus.setTextColor(color);
            if (leaveRequest.getFormStatusEnum() != null &&
                    leaveRequest.getFormStatusEnum().equals(FormStatusEnum.PENDING) &&
                    showApprove) {
                binding.buttonContainer.setVisibility(View.VISIBLE);
                viewModel = new ViewModelProvider(requireActivity()).get(LeaveRequestViewModel.class);
            } else {
                binding.buttonContainer.setVisibility(GONE);
            }
        } else {
            binding.buttonContainer.setVisibility(GONE);
        }

        binding.btnApprove.setOnClickListener(v -> {
            dismiss();
            if (viewModel != null && leaveRequest != null) {
                viewModel.confirmApplication(leaveRequest.getId(), FormStatusEnum.APPROVED);
            }
        });

        binding.btnReject.setOnClickListener(v -> {
            dismiss();
            if (viewModel != null && leaveRequest != null) {
                viewModel.confirmApplication(leaveRequest.getId(), FormStatusEnum.REJECTED);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
