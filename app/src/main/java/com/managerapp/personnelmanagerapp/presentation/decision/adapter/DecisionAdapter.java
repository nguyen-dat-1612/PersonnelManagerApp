package com.managerapp.personnelmanagerapp.presentation.decision.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.databinding.ItemDecisionBinding;
import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.utils.DateTimeUtils;

import java.util.function.Consumer;

public class DecisionAdapter extends ListAdapter<Decision, DecisionAdapter.DecisionViewHolder> {

    private final Consumer<String> onDecisionClickListener;

    public DecisionAdapter(Consumer<String> onDecisionClickListener) {
        super(DIFF_CALLBACK);
        this.onDecisionClickListener = onDecisionClickListener;
    }

    private static final DiffUtil.ItemCallback<Decision> DIFF_CALLBACK = new DiffUtil.ItemCallback<Decision>() {
        @Override
        public boolean areItemsTheSame(@NonNull Decision oldItem, @NonNull Decision newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Decision oldItem, @NonNull Decision newItem) {
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

    class DecisionViewHolder extends RecyclerView.ViewHolder {
        private final ItemDecisionBinding binding;

        public DecisionViewHolder(@NonNull ItemDecisionBinding binding, Consumer<String> onDecisionClickListener) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Decision decision) {
            Context context = binding.getRoot().getContext();
            binding.tvType.setText(
                    context.getString(
                            R.string.decision_type,
                            context.getString(decision.getType().getStringRes())
                    )
            );
            binding.tvContent.setText(context.getString(R.string.decision_content, decision.getContent()));
            binding.tvDate.setText(context.getString(R.string.decision_date, DateTimeUtils.formatSendDate(decision.getDate())));
            binding.tvUser.setText(context.getString(R.string.decision_user, decision.getUser().getFullName()));
            binding.getRoot().setOnClickListener(v -> {
                if (decision != null && onDecisionClickListener != null) {
                    onDecisionClickListener.accept(decision.getId());
                }
            });
        }
    }
}
