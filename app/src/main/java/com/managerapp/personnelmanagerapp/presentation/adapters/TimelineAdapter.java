package com.managerapp.personnelmanagerapp.presentation.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.managerapp.personnelmanagerapp.R;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {

    private List<TimelineModel> timelineList;
    private Context context;

    public TimelineAdapter(Context context, List<TimelineModel> timelineList) {
        this.context = context;
        this.timelineList = timelineList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent, false);
        return new TimelineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        TimelineModel model = timelineList.get(position);
        holder.tvSubtitle.setText(model.getTitle());

        // Xác định viewType nếu bạn dùng (ví dụ: TimelineView.getTimeLineViewType())
        int viewType = TimelineView.getTimeLineViewType(position, getItemCount());

        holder.timelineView.initLine(viewType); // Bắt buộc phải gọi để vẽ đúng

        // Set màu marker
        holder.timelineView.setMarkerColor(Color.parseColor("#FF0000"));

        // Set màu line (trên và dưới)
        holder.timelineView.setStartLineColor(Color.parseColor("#FF9999"), viewType);
        holder.timelineView.setEndLineColor(Color.parseColor("#FF9999"), viewType);
    }

    @Override
    public int getItemCount() {
        return timelineList.size();
    }

    static class TimelineViewHolder extends RecyclerView.ViewHolder {

        TimelineView timelineView;
        TextView tvSubtitle;

        public TimelineViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            timelineView = itemView.findViewById(R.id.timelineView);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
        }
    }
}
