package com.managerapp.personnelmanagerapp.presentation.leaveapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ItemLeaveApplicationBinding;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.ui.LeaveDetailBottomSheetFragment;
import com.managerapp.personnelmanagerapp.utils.DateUtils;

public class LeaveApplicationAdapter extends ListAdapter<LeaveApplication, LeaveApplicationAdapter.ViewHolder> {

    private final Context context;

    public LeaveApplicationAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<LeaveApplication> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<LeaveApplication>() {
                @Override
                public boolean areItemsTheSame(@NonNull LeaveApplication oldItem, @NonNull LeaveApplication newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull LeaveApplication oldItem, @NonNull LeaveApplication newItem) {
                    // Compare all relevant fields that affect the UI
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLeaveApplicationBinding binding = ItemLeaveApplicationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaveApplication leaveApplication = getItem(position);

        ItemLeaveApplicationBinding binding = holder.binding;

        binding.tvLeaveId.setText(context.getString(R.string.leave_form_prefix) + leaveApplication.getId());
        binding.tvLeaveDates.setText(DateUtils.formatDateToVietnameseNoTime(leaveApplication.getStartDate())
                + " → " + DateUtils.formatDateToVietnameseNoTime(leaveApplication.getEndDate()));
        binding.tvLeaveType.setText(context.getString(R.string.leave_type_label_leave_type, leaveApplication.getLeaveTypeName()));
        binding.tvUser.setText(context.getString(R.string.employee_label, leaveApplication.getUser().getFullName()));
        binding.tvReason.setText(context.getString(R.string.reason_label_reason, leaveApplication.getReason()));

        String signerName = leaveApplication.getSigner() != null && leaveApplication.getSigner().getFullName() != null
                ? leaveApplication.getSigner().getFullName()
                : context.getString(R.string.signer_none);
        binding.tvSigner.setText(context.getString(R.string.signer_label, signerName));

        holder.binding.tvLeaveStatus.setText(context.getString(leaveApplication.getFormStatusEnum().getLocalizedStringRes()));

        binding.cardViewLeaveApplication.setOnClickListener(v -> {
            LeaveDetailBottomSheetFragment bottomSheetFragment = new LeaveDetailBottomSheetFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("leaveRequest", leaveApplication);
            bundle.putBoolean("SHOW_APPROVE", false);
            bottomSheetFragment.setArguments(bundle);
            bottomSheetFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), bottomSheetFragment.getTag());
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLeaveApplicationBinding binding;

        public ViewHolder(@NonNull ItemLeaveApplicationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}