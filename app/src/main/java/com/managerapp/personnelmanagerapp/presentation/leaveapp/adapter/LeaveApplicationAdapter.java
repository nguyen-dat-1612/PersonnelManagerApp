package com.managerapp.personnelmanagerapp.presentation.leaveapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.databinding.ItemLeaveApplicationBinding;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.ui.LeaveDetailBottomSheetFragment;

public class LeaveApplicationAdapter extends ListAdapter<LeaveApplicationResponse, LeaveApplicationAdapter.ViewHolder> {

    private final Context context;

    public LeaveApplicationAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    private static final DiffUtil.ItemCallback<LeaveApplicationResponse> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<LeaveApplicationResponse>() {
                @Override
                public boolean areItemsTheSame(@NonNull LeaveApplicationResponse oldItem, @NonNull LeaveApplicationResponse newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull LeaveApplicationResponse oldItem, @NonNull LeaveApplicationResponse newItem) {
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
        LeaveApplicationResponse leaveApplication = getItem(position);
        holder.binding.setLeaveApplication(leaveApplication);
        holder.binding.executePendingBindings();
        holder.binding.cardViewLeaveApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaveDetailBottomSheetFragment bottomSheetFragment = new LeaveDetailBottomSheetFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("leaveRequest", leaveApplication);
                bundle.putBoolean("SHOW_APPROVE", false);
                bottomSheetFragment.setArguments(bundle);

                bottomSheetFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
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