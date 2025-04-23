package com.managerapp.personnelmanagerapp.presentation.leaveapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.databinding.ItemLeaveRequestBinding;

import java.util.List;
import java.util.function.Consumer;

public class LeaveRequestAdapter extends RecyclerView.Adapter<LeaveRequestAdapter.LeaveRequestViewHolder> {

    private final  List<LeaveApplicationResponse> leaveRequests;
    private final Consumer<LeaveApplicationResponse> rejectClickListener;
    private final  Consumer<LeaveApplicationResponse> approveClickListener;
    private final Context context;
    public LeaveRequestAdapter(List<LeaveApplicationResponse> leaveRequests, Consumer<LeaveApplicationResponse> rejectClickListener, Consumer<LeaveApplicationResponse> approveClickListener, Context context) {
        this.leaveRequests = leaveRequests;
        this.rejectClickListener = rejectClickListener;
        this.approveClickListener = approveClickListener;

        this.context = context;
    }

    @Override
    public LeaveRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLeaveRequestBinding binding = ItemLeaveRequestBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LeaveRequestViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(LeaveRequestViewHolder holder, int position) {
        LeaveApplicationResponse leaveRequest = leaveRequests.get(position);
        holder.bind(leaveRequest);
    }

    @Override
    public int getItemCount() {
        return leaveRequests.size();
    }

    public class LeaveRequestViewHolder extends RecyclerView.ViewHolder {

        ItemLeaveRequestBinding binding;

        public LeaveRequestViewHolder(ItemLeaveRequestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind( LeaveApplicationResponse leaveRequest) {
            binding.setLeaveRequest(leaveRequest);

            binding.btnReject.setOnClickListener(v -> {
                rejectClickListener.accept(leaveRequest);
            });

            binding.btnApprove.setOnClickListener(v -> {
                approveClickListener.accept(leaveRequest);
            });

            binding.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LeaveDetailBottomSheetFragment bottomSheetFragment = new LeaveDetailBottomSheetFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("leaveRequest", leaveRequest);
                    bundle.putBoolean("SHOW_APPROVE", true);
                    bottomSheetFragment.setArguments(bundle);

                    bottomSheetFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), bottomSheetFragment.getTag());
                }
            });
        }
    }
}