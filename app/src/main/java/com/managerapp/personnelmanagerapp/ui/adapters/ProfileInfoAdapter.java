package com.managerapp.personnelmanagerapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ItemProfileInfoBinding;
import com.managerapp.personnelmanagerapp.domain.model.DataItem;
import com.managerapp.personnelmanagerapp.domain.model.Employee;

import java.util.List;
import java.util.function.Consumer;

public class ProfileInfoAdapter extends RecyclerView.Adapter<ProfileInfoAdapter.ProfileInfoViewHolder> {

    private List<DataItem> dataList;

    public ProfileInfoAdapter(List<DataItem> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ProfileInfoAdapter.ProfileInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProfileInfoBinding binding = ItemProfileInfoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ProfileInfoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileInfoAdapter.ProfileInfoViewHolder holder, int position) {
        DataItem item = dataList.get(position);
        holder.binding.titleTx.setText(item.getKey());
        holder.binding.infomationTx.setText(item.getValue());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ProfileInfoViewHolder extends RecyclerView.ViewHolder {
        ItemProfileInfoBinding binding;

        public ProfileInfoViewHolder(@NonNull ItemProfileInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
