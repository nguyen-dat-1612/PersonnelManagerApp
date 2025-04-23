package com.managerapp.personnelmanagerapp.presentation.leaveapp;

import static android.view.View.GONE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentLeaveDetailBottomSheetBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LeaveDetailBottomSheetFragment extends BottomSheetDialogFragment {

    private FragmentLeaveDetailBottomSheetBinding binding;
    private LeaveRequestViewModel viewModel;
    private LeaveApplicationResponse leaveRequest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Nhận dữ liệu từ Bundle
        Bundle args = getArguments();
        if (args != null) {
            leaveRequest = (LeaveApplicationResponse) args.getSerializable("leaveRequest");
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
            binding.tvDate.setText(leaveRequest.getStartDate() + " - " + leaveRequest.getEndDate());
            binding.tvReason.setText(leaveRequest.getReason());
            binding.tvStatus.setText(leaveRequest.getFormStatusEnumColor());

            // Kiểm tra quyền phê duyệt và trạng thái "PENDING"
            if (leaveRequest.getFormStatusEnum() != null &&
                    leaveRequest.getFormStatusEnum().equals("PENDING") &&
                    showApprove) {
                binding.buttonContainer.setVisibility(View.VISIBLE);
                // Khởi tạo ViewModel khi có quyền phê duyệt
                viewModel = new ViewModelProvider(requireActivity()).get(LeaveRequestViewModel.class);
            } else {
                binding.buttonContainer.setVisibility(GONE);
            }
        } else {
            binding.buttonContainer.setVisibility(GONE); // fallback
        }

        // Nút duyệt
        binding.btnApprove.setOnClickListener(v -> {
            dismiss();
            // Gửi yêu cầu phê duyệt
            if (viewModel != null && leaveRequest != null) {
                viewModel.confirmApplication(leaveRequest.getId(), "APPROVED");
            }
        });

        // Nút từ chối
        binding.btnReject.setOnClickListener(v -> {
            dismiss();
            // Gửi yêu cầu từ chối
            if (viewModel != null && leaveRequest != null) {
                viewModel.confirmApplication(leaveRequest.getId(), "REJECTED");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
