package com.managerapp.personnelmanagerapp.presentation.notification.adapter;

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

    // Kiểu ViewHolder cho item bình thường
    private static final int TYPE_ITEM = 0;
    // Kiểu ViewHolder cho item loading (footer)
    private static final int TYPE_LOADING = 1;

    private List<NotificationRecipient> notificationList = new ArrayList<>();
    private boolean isLoadingAdded = false; // cờ xác định có thêm footer loading hay không
    private final Consumer<NotificationRecipient> onClick; // callback khi click item

    public HistoryNotificationAdapter(Consumer<NotificationRecipient> onClick) {
        this.onClick = onClick;
    }

    @Override
    public int getItemViewType(int position) {
        // Nếu là vị trí cuối cùng và có loading footer thì trả về TYPE_LOADING, ngược lại TYPE_ITEM
        return (position == notificationList.size() && isLoadingAdded) ? TYPE_LOADING : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        // Tổng số item = số phần tử + 1 nếu có loading footer
        return notificationList.size() + (isLoadingAdded ? 1 : 0);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING) {
            // Inflate layout loading footer
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        } else {
            // Inflate layout item thông báo
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
        // LoadingViewHolder không cần bind dữ liệu
    }

    // ViewHolder cho item thông báo
    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemNotificationHistoryBinding binding;

        public HistoryViewHolder(@NonNull ItemNotificationHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NotificationRecipient item) {
            // Set dữ liệu lên các view trong item
            binding.tvTitle.setText("Tiêu đề: " + item.getTitle());
            binding.tvTime.setText("Thời gian: " + DateTimeUtils.formatSendDate(item.getSendDate())); // hoặc item.getSentAt().toString()

            // Xử lý click item
            binding.getRoot().setOnClickListener(v -> onClick.accept(item));
        }
    }

    // Thêm footer loading
    public void addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true;
            notifyItemInserted(notificationList.size());
        }
    }

    // Xóa footer loading
    public void removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false;
            notifyItemRemoved(notificationList.size());
        }
    }

    // Thêm danh sách mới vào cuối danh sách hiện tại
    public void addAll(List<NotificationRecipient> newItems) {
        int startPos = notificationList.size();
        notificationList.addAll(newItems);
        notifyItemRangeInserted(startPos, newItems.size());
    }

    // Cập nhật lại toàn bộ danh sách, xóa hết rồi set mới
    public void updateList(List<NotificationRecipient> newList) {
        notificationList.clear();
        notificationList.addAll(newList);
        notifyDataSetChanged();
    }

    // ViewHolder cho loading footer
    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}