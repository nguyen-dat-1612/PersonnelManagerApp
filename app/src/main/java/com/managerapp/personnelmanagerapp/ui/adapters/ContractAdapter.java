package com.managerapp.personnelmanagerapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.databinding.ItemContractBinding;
import com.managerapp.personnelmanagerapp.domain.model.Contract;

import java.util.List;
import java.util.function.Consumer;

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ContractViewHolder> {
    private List<ContractResponse> contractList;
    private Context context;
    private Consumer<Integer> consumer;

    public ContractAdapter(List<ContractResponse> contractList, Context context, Consumer<Integer> consumer) {
        this.contractList = contractList;
        this.context = context;
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
        ContractResponse contract = contractList.get(position);
        holder.binding.setContract(contract);
        holder.itemView.setOnClickListener(v -> {
            consumer.accept(contract.getId());
        });
    }

    @Override
    public int getItemCount() {
        return contractList.size();
    }

    static class ContractViewHolder extends RecyclerView.ViewHolder {
        private final ItemContractBinding binding;

        public ContractViewHolder(@NonNull ItemContractBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}