package com.managerapp.personnelmanagerapp.presentation.profile.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.databinding.ItemProfileInfoBinding;
import com.managerapp.personnelmanagerapp.domain.model.DataItem;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileInfoViewHolder> {

    private final List<DataItem> dataList;

    public ProfileAdapter(List<DataItem> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ProfileAdapter.ProfileInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProfileInfoBinding binding = ItemProfileInfoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ProfileInfoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ProfileInfoViewHolder holder, int position) {
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
