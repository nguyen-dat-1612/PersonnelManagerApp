package com.managerapp.personnelmanagerapp.presentation.worklog;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.databinding.ItemTimelineBinding;

import java.util.List;

public class WorkLogAdapter extends RecyclerView.Adapter<WorkLogAdapter.WorkLogViewHolder> {

    private final List<WorkLogResponse> workLogResponseList;

    public WorkLogAdapter(List<WorkLogResponse> workLogResponseList) {
        this.workLogResponseList = workLogResponseList;
    }

    @NonNull
    @Override
    public WorkLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTimelineBinding binding = ItemTimelineBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new WorkLogViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkLogViewHolder holder, int position) {
        holder.bind(workLogResponseList.get(position), position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return workLogResponseList.size();
    }

    static class WorkLogViewHolder extends RecyclerView.ViewHolder{
        ItemTimelineBinding binding;

        public WorkLogViewHolder(@NonNull ItemTimelineBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(WorkLogResponse workLogResponse, int position, int totalCount)  {
            binding.setWorklog(workLogResponse);

//            int viewType = TimelineView.getTimeLineViewType(position, totalCount);

//            binding.timelineView.initLine(viewType);
//
//            binding.timelineView.setMarkerColor(Color.parseColor("#FF0000"));
//
//            binding.timelineView.setStartLineColor(Color.parseColor("#FF9999"), viewType);
//            binding.timelineView.setEndLineColor(Color.parseColor("#FF9999"), viewType);
        }
    }
}
