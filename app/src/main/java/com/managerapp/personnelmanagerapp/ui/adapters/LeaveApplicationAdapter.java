package com.managerapp.personnelmanagerapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.databinding.ItemLeaveApplicationBinding;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;

import java.util.List;
import java.util.function.Consumer;

public class LeaveApplicationAdapter extends RecyclerView.Adapter<LeaveApplicationAdapter.ViewHolder> {

    private List<LeaveApplication> leaveList;
    private Consumer<LeaveApplication> consumer;

    public LeaveApplicationAdapter(List<LeaveApplication> leaveList, Consumer<LeaveApplication> consumer) {
        this.leaveList = leaveList;
        this.consumer = consumer;
    }

    @NonNull
    @Override
    public LeaveApplicationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLeaveApplicationBinding binding =  ItemLeaveApplicationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveApplicationAdapter.ViewHolder holder, int position) {
        LeaveApplication leaveApplication = leaveList.get(position);
        holder.binding.setLeaveApplication(leaveApplication);
        holder.binding.executePendingBindings();
        holder.binding.cardViewLeaveApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumer.accept(leaveApplication);
            }
        });

    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final ItemLeaveApplicationBinding binding;
        public ViewHolder(@NonNull ItemLeaveApplicationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
