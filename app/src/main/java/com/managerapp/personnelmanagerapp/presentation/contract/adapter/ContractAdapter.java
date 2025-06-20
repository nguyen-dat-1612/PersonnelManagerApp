package com.managerapp.personnelmanagerapp.presentation.contract.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ItemContractBinding;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import java.util.function.Consumer;

public class ContractAdapter extends ListAdapter<Contract, ContractAdapter.ContractViewHolder> {
    private final Consumer<Integer> consumer;
    public ContractAdapter(Consumer<Integer> consumer) {
        super(DIFF_CALLBACK);
        this.consumer = consumer;
    }
    @NonNull
    @Override
    public ContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContractBinding binding = ItemContractBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ContractViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull ContractViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
    class ContractViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final ItemContractBinding binding;

        public ContractViewHolder(@NonNull ItemContractBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Contract c) {
            Context context = binding.getRoot().getContext();
            binding.tvContractId.setText(context.getString(R.string.contract_id) + " " + c.getId());
            binding.tvStatus.setText(c.getContractStatusEnum().getIconWithText(context));
            binding.getRoot().setOnClickListener(v -> consumer.accept(c.getId()));
        }

    }
    private static final DiffUtil.ItemCallback<Contract> DIFF_CALLBACK = new DiffUtil.ItemCallback<Contract>() {
        @Override
        public boolean areItemsTheSame(@NonNull Contract oldItem, @NonNull Contract newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contract oldItem, @NonNull Contract newItem) {
            return oldItem.equals(newItem);
        }
    };
}
