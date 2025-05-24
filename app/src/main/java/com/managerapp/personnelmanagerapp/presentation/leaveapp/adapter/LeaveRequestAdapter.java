package com.managerapp.personnelmanagerapp.presentation.leaveapp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.databinding.ItemLeaveRequestBinding;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.ui.LeaveDetailBottomSheetFragment;

import java.util.List;
import java.util.function.Consumer;

import io.reactivex.rxjava3.annotations.NonNull;

public class LeaveRequestAdapter extends ListAdapter<LeaveApplicationResponse, LeaveRequestAdapter.LeaveRequestViewHolder> {

    private final Consumer<LeaveApplicationResponse> rejectClickListener;
    private final Consumer<LeaveApplicationResponse> approveClickListener;
    private final FragmentActivity activity;

    public LeaveRequestAdapter(
            Consumer<LeaveApplicationResponse> rejectClickListener,
            Consumer<LeaveApplicationResponse> approveClickListener,
            FragmentActivity activity) {
        super(DIFF_CALLBACK);
        this.rejectClickListener = rejectClickListener;
        this.approveClickListener = approveClickListener;
        this.activity = activity;
    }

    public static final DiffUtil.ItemCallback<LeaveApplicationResponse> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<LeaveApplicationResponse>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull LeaveApplicationResponse oldItem, @NonNull LeaveApplicationResponse newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull LeaveApplicationResponse oldItem, @NonNull LeaveApplicationResponse newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @Override
    public LeaveRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLeaveRequestBinding binding = ItemLeaveRequestBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LeaveRequestViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(LeaveRequestViewHolder holder, int position) {
        LeaveApplicationResponse leaveRequest = getItem(position);
        holder.bind(leaveRequest);
    }

    public class LeaveRequestViewHolder extends RecyclerView.ViewHolder {

        ItemLeaveRequestBinding binding;

        public LeaveRequestViewHolder(ItemLeaveRequestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(LeaveApplicationResponse leaveRequest) {
            binding.setLeaveRequest(leaveRequest);

            binding.btnReject.setOnClickListener(v -> rejectClickListener.accept(leaveRequest));
            binding.btnApprove.setOnClickListener(v -> approveClickListener.accept(leaveRequest));

            binding.itemView.setOnClickListener(v -> {
                LeaveDetailBottomSheetFragment bottomSheetFragment = new LeaveDetailBottomSheetFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("leaveRequest", leaveRequest);
                bundle.putBoolean("SHOW_APPROVE", true);
                bottomSheetFragment.setArguments(bundle);

                bottomSheetFragment.show(activity.getSupportFragmentManager(), bottomSheetFragment.getTag());
            });
        }
    }
}
