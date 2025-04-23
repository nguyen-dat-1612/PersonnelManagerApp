package com.managerapp.personnelmanagerapp.presentation.salary;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.databinding.ItemSalaryBinding;
import com.managerapp.personnelmanagerapp.domain.model.Salary;

import java.util.List;
import java.util.function.Consumer;

public class SalaryAdapter extends RecyclerView.Adapter<SalaryAdapter.SalaryViewHolder> {

    private final List<Salary> salaryList;
    private final Consumer<String> consumer;

    public SalaryAdapter(List<Salary> salaryList, Consumer<String> consumer) {
        this.salaryList = salaryList;
        this.consumer = consumer;
    }

    @NonNull
    @Override
    public SalaryAdapter.SalaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSalaryBinding binding = ItemSalaryBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new SalaryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SalaryAdapter.SalaryViewHolder holder, int position) {
        Salary salary = salaryList.get(position);
        holder.binding.setSalary(salary);
        holder.binding.cardViewSalary.setOnClickListener(v -> consumer.accept(salary.getId()));
    }

    @Override
    public int getItemCount() {
        return salaryList.size();
    }

    static class SalaryViewHolder extends RecyclerView.ViewHolder {
        ItemSalaryBinding binding;

        public SalaryViewHolder(@NonNull  ItemSalaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
