package com.managerapp.personnelmanagerapp.presentation.decision;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.databinding.ItemDecisionBinding;

import java.util.List;
import java.util.function.Consumer;

public class DecisionAdapter extends RecyclerView.Adapter<DecisionAdapter.DecisionViewHolder> {
    private final List<DecisionResponse> decisionResponses;
    private final Consumer<String> onDecisionClickListener;

    public DecisionAdapter(List<DecisionResponse> decisionResponses, Consumer<String> onDecisionClickListener) {
        this.decisionResponses = decisionResponses;
        this.onDecisionClickListener = onDecisionClickListener;
    }

    @NonNull
    @Override
    public DecisionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDecisionBinding binding = ItemDecisionBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new DecisionViewHolder(binding, onDecisionClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DecisionViewHolder holder, int position) {
        DecisionResponse decisionResponse = decisionResponses.get(position);
        holder.bind(decisionResponse);
    }

    @Override
    public int getItemCount() {
        return decisionResponses.size();
    }

    static class DecisionViewHolder extends RecyclerView.ViewHolder {
        private final ItemDecisionBinding binding;

        public DecisionViewHolder(@NonNull ItemDecisionBinding binding, Consumer<String> onDecisionClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                if (onDecisionClickListener != null) {
                    onDecisionClickListener.accept(binding.getDecision().getId());
                }
            });
        }

        public void bind(DecisionResponse decisionResponse) {
            binding.setDecision(decisionResponse);
        }
    }
}