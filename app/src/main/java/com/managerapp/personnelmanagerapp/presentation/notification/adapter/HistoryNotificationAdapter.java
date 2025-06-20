package com.managerapp.personnelmanagerapp.presentation.notification.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ItemNotificationHistoryBinding;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HistoryNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_LOADING = 1;

    private List<NotificationRecipient> notificationList = new ArrayList<>();
    private boolean isLoadingAdded = false;
    private final Consumer<NotificationRecipient> onClick;

    public HistoryNotificationAdapter(Consumer<NotificationRecipient> onClick) {
        this.onClick = onClick;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == notificationList.size() && isLoadingAdded) ? TYPE_LOADING : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return notificationList.size() + (isLoadingAdded ? 1 : 0);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        } else {
            ItemNotificationHistoryBinding binding = ItemNotificationHistoryBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false);
            return new HistoryViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            ((HistoryViewHolder) holder).bind(notificationList.get(position));
        }
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemNotificationHistoryBinding binding;

        public HistoryViewHolder(@NonNull ItemNotificationHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NotificationRecipient item) {
            Context context = binding.getRoot().getContext();

            String titlePrefix = context.getString(R.string.title_prefix);
            String timePrefix = context.getString(R.string.time_prefix);

            binding.tvTitle.setText(titlePrefix + item.getTitle());
            binding.tvTime.setText(timePrefix + DateTimeUtils.formatSendDate(item.getSendDate()));

            binding.getRoot().setOnClickListener(v -> onClick.accept(item));
        }
    }

    public void addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true;
            notifyItemInserted(notificationList.size());
        }
    }

    public void removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false;
            notifyItemRemoved(notificationList.size());
        }
    }

    public void addAll(List<NotificationRecipient> newItems) {
        int startPos = notificationList.size();
        notificationList.addAll(newItems);
        notifyItemRangeInserted(startPos, newItems.size());
    }

    public void updateList(List<NotificationRecipient> newList) {
        notificationList.clear();
        notificationList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}