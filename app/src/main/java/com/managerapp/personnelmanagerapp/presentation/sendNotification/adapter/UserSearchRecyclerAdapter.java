package com.managerapp.personnelmanagerapp.presentation.sendNotification.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ItemUserSearchBinding;
import com.managerapp.personnelmanagerapp.domain.model.UserSummary;
import java.util.List;
import java.util.function.Consumer;

public class UserSearchRecyclerAdapter extends RecyclerView.Adapter<UserSearchRecyclerAdapter.UserViewHolder> {
    private List<UserSummary> userList;
    private Consumer<UserSummary> onUserClick;

    public UserSearchRecyclerAdapter(List<UserSummary> userList, Consumer<UserSummary> onUserClick) {
        this.userList = userList;
        this.onUserClick = onUserClick;
    }

    public void updateData(List<UserSummary> newUsers) {
        this.userList = newUsers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(ItemUserSearchBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserSummary user = userList.get(position);
        holder.bind(user);
        holder.itemView.setOnClickListener(v -> onUserClick.accept(user));
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size() : 0;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        ItemUserSearchBinding binding;
        public UserViewHolder(@NonNull  ItemUserSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserSummary user) {
            Context context = binding.getRoot().getContext();
            binding.userNameTextView.setText(user.getFullName());
            binding.userIdTextView.setText(
                    context.getString(
                            R.string.label_lecturer_id_with_value,
                            String.valueOf(user.getId())
                    )
            );
        }
    }
}
