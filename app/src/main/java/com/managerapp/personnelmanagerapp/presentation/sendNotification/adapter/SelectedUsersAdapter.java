package com.managerapp.personnelmanagerapp.presentation.sendNotification.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.function.Consumer;
import com.managerapp.personnelmanagerapp.databinding.ItemSelectedUserBinding;
import com.managerapp.personnelmanagerapp.domain.model.UserSummary;

public class SelectedUsersAdapter extends RecyclerView.Adapter<SelectedUsersAdapter.UserViewHolder> {
    private List<UserSummary> selectedUsers;
    private Consumer<UserSummary> onUserRemove;

    public SelectedUsersAdapter(List<UserSummary> selectedUsers, Consumer<UserSummary> removeListener) {
        this.selectedUsers = selectedUsers;
        this.onUserRemove = removeListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(ItemSelectedUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserSummary user = selectedUsers.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return selectedUsers.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        ItemSelectedUserBinding binding;
        UserViewHolder(@NonNull  ItemSelectedUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(UserSummary user) {
            binding.setUserSummary(user);
            binding.removeUserImageView.setOnClickListener(v -> {
                if (onUserRemove != null) {
                    onUserRemove.accept(user);
                }
            });
        }
    }
    public void submitList(List<UserSummary> selectedUsers) {
        this.selectedUsers = selectedUsers;
        notifyDataSetChanged();
    }
}
