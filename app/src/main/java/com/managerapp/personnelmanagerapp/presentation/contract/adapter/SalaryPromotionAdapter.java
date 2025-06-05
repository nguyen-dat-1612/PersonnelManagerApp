package com.managerapp.personnelmanagerapp.presentation.contract.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.R;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salary_promotion, parent, false);
        return new SalaryPromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalaryPromotionViewHolder holder, int position) {
        SalaryPromotion promotion = getItem(position);
        holder.bind(promotion);
    }

    class SalaryPromotionViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvUserName;
        private final TextView tvReason;
        private final TextView tvStatus;

        public SalaryPromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvReason = itemView.findViewById(R.id.tvReason);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }

        public void bind(SalaryPromotion promotion) {
            tvUserName.setText(promotion.getUserName());
            tvReason.setText("Lý do: " + promotion.getReason());
            tvStatus.setText("Trạng thái: " + promotion.getStatus());

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
            return oldItem.equals(newItem);
        }
    };
}
