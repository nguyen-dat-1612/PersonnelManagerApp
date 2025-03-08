package com.managerapp.personnelmanagerapp.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.R;

import java.util.Calendar;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    private final Context context;
    private final List<Integer> dates;
    private int selectedPosition = -1; // Ngày được chọn
    private final int today; // Ngày hôm nay

    public DateAdapter(Context context, List<Integer> dates) {
        this.context = context;
        this.dates = dates;

        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        today = calendar.get(Calendar.DAY_OF_MONTH);

        // Chọn ngày hôm nay mặc định
        selectedPosition = dates.indexOf(today);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_date, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int day = dates.get(position);
        holder.tvDate.setText(String.valueOf(day));

        // Đặt mặc định (tránh lỗi giữ màu cũ)
        holder.tvDate.setTextColor(Color.BLACK);
        holder.tvDate.setBackgroundResource(R.drawable.bg_circle);

        // Ngày đầu hoặc cuối tuần (viền đỏ)
        if (position == 0 || position == dates.size() - 1) {
            holder.tvDate.setBackgroundResource(R.drawable.border_red);
        }

        // Ngày hiện tại (viền xanh)
        if (day == today) {
            holder.tvDate.setBackgroundResource(R.drawable.border_green);
        }

        // Ngày được chọn (nền đỏ, chữ trắng)
        if (position == selectedPosition) {
            holder.tvDate.setTextColor(Color.WHITE);
            holder.tvDate.setBackgroundResource(R.drawable.bg_selected);
        }

        // Sự kiện chọn ngày
        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

    // Cập nhật ngày được chọn từ bên ngoài (Fragment)
    public void setSelectedDate(int today) {
        selectedPosition = dates.indexOf(today);
        notifyDataSetChanged();
    }
}
