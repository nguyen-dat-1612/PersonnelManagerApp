package com.managerapp.personnelmanagerapp.presentation.leaveapp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.managerapp.personnelmanagerapp.databinding.ItemLeaveRequestBinding;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.ui.LeaveDetailBottomSheetFragment;
import java.util.function.Consumer;
import io.reactivex.rxjava3.annotations.NonNull;
import com.managerapp.personnelmanagerapp.R;

public class LeaveRequestAdapter extends ListAdapter<LeaveApplication, LeaveRequestAdapter.LeaveRequestViewHolder> {
    private final Consumer<LeaveApplication> rejectClickListener;
    private final Consumer<LeaveApplication> approveClickListener;
    private final FragmentActivity activity;

    public LeaveRequestAdapter(
            Consumer<LeaveApplication> rejectClickListener,
            Consumer<LeaveApplication> approveClickListener,
            FragmentActivity activity) {
        super(DIFF_CALLBACK);
        this.rejectClickListener = rejectClickListener;
        this.approveClickListener = approveClickListener;
        this.activity = activity;
    }

    public static final DiffUtil.ItemCallback<LeaveApplication> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<LeaveApplication>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull LeaveApplication oldItem, @NonNull LeaveApplication newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull LeaveApplication oldItem, @NonNull LeaveApplication newItem) {
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
        LeaveApplication leaveRequest = getItem(position);
        holder.bind(leaveRequest);
    }

    public class LeaveRequestViewHolder extends RecyclerView.ViewHolder {

        ItemLeaveRequestBinding binding;

        public LeaveRequestViewHolder(ItemLeaveRequestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(LeaveApplication leaveRequest) {
            binding.setLeaveRequest(leaveRequest);

            binding.btnReject.setOnClickListener(v -> rejectClickListener.accept(leaveRequest));
            binding.btnApprove.setOnClickListener(v -> approveClickListener.accept(leaveRequest));

            int color;
            if (leaveRequest.getFormStatusEnum().equals(FormStatusEnum.PENDING)) {
                color = binding.getRoot().getContext().getColor(R.color.orange);
            } else if (leaveRequest.getFormStatusEnum().equals(FormStatusEnum.REJECTED)) {
                color = binding.getRoot().getContext().getColor(R.color.red);
            } else {
                color = binding.getRoot().getContext().getColor(R.color.green);
            }

            binding.tvStatus.setTextColor(color);

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
