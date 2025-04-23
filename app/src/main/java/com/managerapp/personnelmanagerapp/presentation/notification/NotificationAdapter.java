package com.managerapp.personnelmanagerapp.presentation.notification;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientEntity;
import com.managerapp.personnelmanagerapp.databinding.ItemNotificationBinding;

import java.util.List;
import java.util.function.Consumer;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final List<NotificationRecipientEntity> notificationList;
    private final Consumer<NotificationRecipientEntity> onItemClick;

    public NotificationAdapter(List<NotificationRecipientEntity> notificationList,Consumer<NotificationRecipientEntity> onItemClick ) {
        this.notificationList = notificationList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        NotificationRecipientEntity notification = notificationList.get(position);

        holder.binding.setNotification(notification);
        // Gán sự kiện click bằng lambda
        holder.itemView.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.accept(notification); // Gọi lambda với đối tượng notification
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList != null ? notificationList.size() : 0;
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;

        NotificationViewHolder(@NonNull ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
