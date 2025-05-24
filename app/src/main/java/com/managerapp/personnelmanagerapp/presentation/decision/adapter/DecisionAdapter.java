package com.managerapp.personnelmanagerapp.presentation.decision.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.databinding.ItemDecisionBinding;
import com.managerapp.personnelmanagerapp.utils.DateTimeUtils;

import java.util.function.Consumer;

public class DecisionAdapter extends ListAdapter<DecisionResponse, DecisionAdapter.DecisionViewHolder> {

    private final Consumer<String> onDecisionClickListener;

    public DecisionAdapter(Consumer<String> onDecisionClickListener) {
        super(DIFF_CALLBACK);
        this.onDecisionClickListener = onDecisionClickListener;
    }

    private static final DiffUtil.ItemCallback<DecisionResponse> DIFF_CALLBACK = new DiffUtil.ItemCallback<DecisionResponse>() {
        @Override
        public boolean areItemsTheSame(@NonNull DecisionResponse oldItem, @NonNull DecisionResponse newItem) {
            // So sánh ID
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull DecisionResponse oldItem, @NonNull DecisionResponse newItem) {
            return oldItem.equals(newItem);
        }
    };

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
        holder.bind(getItem(position));
    }

    static class DecisionViewHolder extends RecyclerView.ViewHolder {
        private final ItemDecisionBinding binding;

        public DecisionViewHolder(@NonNull ItemDecisionBinding binding, Consumer<String> onDecisionClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                if (binding.getDecision() != null && onDecisionClickListener != null) {
                    onDecisionClickListener.accept(binding.getDecision().getId());
                }
            });
        }

        public void bind(DecisionResponse decisionResponse) {
            binding.setDecision(decisionResponse);
//            binding.tvFullName.setText(decisionResponse.getFullName());
            binding.tvType.setText("Quyết định: " + decisionResponse.getType());
            binding.tvContent.setText("Nội dung: " + decisionResponse.getContent());
            binding.tvDate.setText("Thời gian: " + DateTimeUtils.formatSendDate(decisionResponse.getDate()));
            binding.executePendingBindings();
        }
    }
}
