package com.managerapp.personnelmanagerapp.presentation.contract.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ItemSalaryPromotionBinding;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;

import java.util.function.Consumer;

    public class SalaryPromotionAdapter extends ListAdapter<SalaryPromotion, SalaryPromotionAdapter.SalaryPromotionViewHolder> {

    private final Consumer<SalaryPromotion> onItemClick;

    public SalaryPromotionAdapter(@NonNull Consumer<SalaryPromotion> onItemClick) {
        super(DIFF_CALLBACK);
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public SalaryPromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSalaryPromotionBinding binding = ItemSalaryPromotionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SalaryPromotionViewHolder(binding, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull SalaryPromotionViewHolder holder, int position) {
        SalaryPromotion promotion = getItem(position);
        holder.bind(promotion);
    }

    static class SalaryPromotionViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        private final ItemSalaryPromotionBinding binding;
        private final Consumer<SalaryPromotion> onItemClick;

        public SalaryPromotionViewHolder(@NonNull ItemSalaryPromotionBinding binding, Consumer<SalaryPromotion> onItemClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClick = onItemClick;
        }
        public void bind(SalaryPromotion promotion) {
            Context context = itemView.getContext();
            binding.tvUserName.setText(promotion.getUserName());
            binding.tvReason.setText(context.getString(R.string.label_reason, promotion.getReason()));
            binding.tvStatus.setText(context.getString(R.string.label_status, getStatusText(context, promotion.getStatus())));
            itemView.setOnClickListener(v -> onItemClick.accept(promotion));
        }
    }

    public static final DiffUtil.ItemCallback<SalaryPromotion> DIFF_CALLBACK = new DiffUtil.ItemCallback<SalaryPromotion>() {
        @Override
        public boolean areItemsTheSame(@NonNull SalaryPromotion oldItem, @NonNull SalaryPromotion newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull SalaryPromotion oldItem, @NonNull SalaryPromotion newItem) {
            return oldItem.getUserName().equals(newItem.getUserName()) &&
                    oldItem.getReason().equals(newItem.getReason()) &&
                    oldItem.getStatus().equals(newItem.getStatus());
        }
    };

        private static String getStatusText(Context context, String status) {
            try {
                FormStatusEnum statusEnum = FormStatusEnum.valueOf(status);
                return context.getString(statusEnum.getLocalizedStringRes());
            } catch (IllegalArgumentException e) {
                return status;
            }
        }
}
