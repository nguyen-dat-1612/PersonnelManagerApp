package com.managerapp.personnelmanagerapp.presentation.notification.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.managerapp.personnelmanagerapp.databinding.ItemNotificationBinding;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.utils.DateTimeUtils;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_LOADING = 1;

    private List<NotificationRecipient> notificationList = new ArrayList<>();
    private final Consumer<NotificationRecipient> onItemClick;
    private boolean isLoadingAdded = false;

    public NotificationAdapter(Consumer<NotificationRecipient> onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemNotificationBinding binding = ItemNotificationBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            );
            return new NotificationViewHolder(binding);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            NotificationRecipient notification = notificationList.get(position);
            NotificationViewHolder viewHolder = (NotificationViewHolder) holder;

            viewHolder.binding.notificationTitle.setText("Tiêu đề: " + notification.getTitle());
            viewHolder.binding.notificationDate.setText("Thời gian: " + DateTimeUtils.formatSendDate(notification.getSendDate()));
            viewHolder.binding.setNotification(notification);
            viewHolder.itemView.setOnClickListener(v -> {
                if (onItemClick != null) {
                    onItemClick.accept(notification);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == notificationList.size() - 1 && isLoadingAdded) ? TYPE_LOADING : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return notificationList != null ? notificationList.size() : 0;
    }

    public void updateList(List<NotificationRecipient> newList) {
        NotificationDiffCallback diffCallback = new NotificationDiffCallback(notificationList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.notificationList.clear();
        this.notificationList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void addItems(List<NotificationRecipient> newItems) {
        int startPosition = notificationList.size();
        this.notificationList.addAll(newItems);
        notifyItemRangeInserted(startPosition, newItems.size());
    }

    public void showLoading(boolean show) {
        if (show) {
            if (!isLoadingAdded) {
                isLoadingAdded = true;
                notificationList.add(new NotificationRecipient(-1, false, "", ""));
                notifyItemInserted(notificationList.size() - 1);
            }
        } else {
            if (isLoadingAdded) {
                isLoadingAdded = false;
                int position = notificationList.size() - 1;
                if (position >= 0) {
                    notificationList.remove(position);
                    notifyItemRemoved(position);
                }
            }
        }
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding binding;

        NotificationViewHolder(@NonNull ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class NotificationDiffCallback extends DiffUtil.Callback {
        private final List<NotificationRecipient> oldList;
        private final List<NotificationRecipient> newList;

        NotificationDiffCallback(List<NotificationRecipient> oldList, List<NotificationRecipient> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            NotificationRecipient oldItem = oldList.get(oldItemPosition);
            NotificationRecipient newItem = newList.get(newItemPosition);

            Bundle diff = new Bundle();
            if (oldItem.isReadStatus() != newItem.isReadStatus()) {
                diff.putBoolean("isReadChanged", true);
            }

            if (diff.size() == 0) return null;
            return diff;
        }
    }
}