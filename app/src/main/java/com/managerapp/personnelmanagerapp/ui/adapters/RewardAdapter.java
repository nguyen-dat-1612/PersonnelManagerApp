package com.managerapp.personnelmanagerapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.databinding.ItemAssignmentBinding;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {
    private List<AssignmentResponse> assignmentResponses;

    public RewardAdapter(List<AssignmentResponse> assignmentResponses) {
        this.assignmentResponses = assignmentResponses;
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAssignmentBinding binding = ItemAssignmentBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new RewardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        AssignmentResponse assignmentResponse = assignmentResponses.get(position);
        holder.binding.setReward(assignmentResponse);
    }

    @Override
    public int getItemCount() {
        return assignmentResponses.size();
    }

    static class RewardViewHolder extends RecyclerView.ViewHolder {
        private final ItemAssignmentBinding binding;
        public RewardViewHolder(@NonNull ItemAssignmentBinding binding) {
            super(binding.getRoot());
            this.binding  = binding;
        }
    }
}
