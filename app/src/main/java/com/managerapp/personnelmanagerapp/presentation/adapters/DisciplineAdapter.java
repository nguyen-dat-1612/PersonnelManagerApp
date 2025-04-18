package com.managerapp.personnelmanagerapp.presentation.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.databinding.ItemAssignmentBinding;

import java.util.List;

public class DisciplineAdapter  extends RecyclerView.Adapter<DisciplineAdapter.DisciplineViewHolder>{
    private List<AssignmentResponse> assignmentResponses;

    public DisciplineAdapter(List<AssignmentResponse> assignmentResponses) {
        this.assignmentResponses = assignmentResponses;
    }

    @NonNull
    @Override
    public DisciplineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAssignmentBinding binding = ItemAssignmentBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new DisciplineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DisciplineAdapter.DisciplineViewHolder holder, int position) {
        AssignmentResponse assignmentResponse = assignmentResponses.get(position);
        holder.binding.setReward(assignmentResponse);
    }

    @Override
    public int getItemCount() {
        return assignmentResponses.size();
    }

    static class DisciplineViewHolder extends RecyclerView.ViewHolder {
        private final ItemAssignmentBinding binding;
        public DisciplineViewHolder(@NonNull ItemAssignmentBinding binding) {
            super(binding.getRoot());
            this.binding  = binding;
        }
    }
}
