package com.managerapp.personnelmanagerapp.presentation.notification.adapter;
import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.utils.PdfUtils;

import java.util.List;

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.ViewHolder> {
    private final List<String> attachments;
    private final Context context;
    public interface OnAttachmentClickListener {
        void onAttachmentClick(String attachment);
    }

    public AttachmentAdapter(List<String> attachments, Context context) {
        this.attachments = attachments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attachment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String attachment = attachments.get(position);
        holder.tvAttachmentName.setText(attachment);
        holder.ivDownload.setOnClickListener(v -> {
                        PdfUtils.setupDownloadButton(context,holder.ivDownload,attachment, attachment);
        });
    }

    @Override
    public int getItemCount() {
        return attachments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvAttachmentName;
        public final ImageView ivDownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttachmentName = itemView.findViewById(R.id.tv_attachment_name);
            ivDownload = itemView.findViewById(R.id.iv_download);
        }
    }
}