package com.managerapp.personnelmanagerapp.presentation.worklog.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractWorkLog;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionWorkLog;
import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.databinding.ItemContractWorklogBinding;
import com.managerapp.personnelmanagerapp.databinding.ItemDecisionWorklogBinding;
import com.managerapp.personnelmanagerapp.utils.CurrencyUtils;
import com.managerapp.personnelmanagerapp.utils.DateUtils;

import java.util.List;

public class WorkLogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<WorkLogResponse> workLogResponseList;

    public WorkLogAdapter(List<WorkLogResponse> workLogResponseList) {
        this.workLogResponseList = workLogResponseList;
    }

    @Override
    public int getItemViewType(int position) {
        WorkLogResponse item = workLogResponseList.get(position);
        if ("CONTRACT_SIGN".equals(item.getType()) || "CONTRACT_RENEWAL".equals(item.getType())) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 0) {
            ItemContractWorklogBinding contractBinding = ItemContractWorklogBinding.inflate(
                    inflater, parent, false);
            return new ContractViewHolder(contractBinding);
        } else {
            ItemDecisionWorklogBinding decisionBinding = ItemDecisionWorklogBinding.inflate(
                    inflater, parent, false);
            return new DecisionViewHolder(decisionBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WorkLogResponse item = workLogResponseList.get(position);

        if (holder instanceof ContractViewHolder && item instanceof ContractWorkLog) {
            ((ContractViewHolder) holder).bind((ContractWorkLog) item, position, getItemCount());
        } else if (holder instanceof DecisionViewHolder && item instanceof DecisionWorkLog) {
            ((DecisionViewHolder) holder).bind((DecisionWorkLog) item, position, getItemCount());
        }
    }

    @Override
    public int getItemCount() {
        return workLogResponseList.size();
    }

    static class ContractViewHolder extends RecyclerView.ViewHolder {
        private final ItemContractWorklogBinding binding;

        public ContractViewHolder(@NonNull ItemContractWorklogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ContractWorkLog workLog, int position, int totalCount) {

            Context context = binding.getRoot().getContext();
            String title = context.getString(
                    R.string.title_contract_date,
                    DateUtils.formatDateToVietnameseNoTime(workLog.getContractResponse().getStartDate()),
                    DateUtils.formatDateToVietnameseNoTime(workLog.getContractResponse().getEndDate())
            );
            binding.tvTitle.setText(title);

            binding.tvContractType.setText(context.getString(
                    R.string.contract_type_special,
                    workLog.getContractResponse().getContractTypeName()
            ));
            binding.tvSalary.setText(context.getString(
                    R.string.decision_value_salary,
                    CurrencyUtils.formatToVNDSimple(workLog.getContractResponse().getBasicSalary())
            ));

            binding.tvSigner.setText(context.getString(
                    R.string.decision_signer,
                    workLog.getContractResponse().getSigner().getFullName()
            ));

            int viewType = TimelineView.getTimeLineViewType(position, totalCount);

            binding.timelineView.initLine(viewType);

            if (position == 0) {
                binding.timelineView.setMarker(ContextCompat.getDrawable(
                        binding.getRoot().getContext(),
                        R.drawable.bg_gradient_red_green
                ));
            } else {
                binding.timelineView.setMarker(ContextCompat.getDrawable(
                        binding.getRoot().getContext(),
                        R.drawable.bg_marker_default
                ));
            }
        }
    }

    static class DecisionViewHolder extends RecyclerView.ViewHolder {
        private final ItemDecisionWorklogBinding binding;

        public DecisionViewHolder(@NonNull ItemDecisionWorklogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DecisionWorkLog workLog, int position, int totalCount) {

            Context context = binding.getRoot().getContext();
            binding.tvDecisionDate.setText(context.getString(
                    R.string.title_decision_date,
                    DateUtils.convertToDayMonthYearFormat(workLog.getDecisionResponse().getDate())
            ));
            binding.tvDecisionType.setText(context.getString(
                    R.string.decision_type_special,
                    workLog.getDecisionResponse().getType().getDecisionString(context,workLog.getDecisionResponse().getType())
            ));
            binding.tvDecisionContent.setText(context.getString(
                    R.string.decision_content_special,
                    workLog.getDecisionResponse().getContent()
            ));

            binding.tvDecisionSigner.setText(context.getString(
                    R.string.decision_signer,
                    workLog.getDecisionResponse().getSigner().getFullName()
            ));


            int viewType = TimelineView.getTimeLineViewType(position, totalCount);
            binding.timelineView.initLine(viewType);

            if (position == 0) {
                binding.timelineView.setMarker(ContextCompat.getDrawable(
                        binding.getRoot().getContext(),
                        R.drawable.bg_gradient_red_green
                ));
            } else {
                binding.timelineView.setMarker(ContextCompat.getDrawable(
                        binding.getRoot().getContext(),
                        R.drawable.bg_marker_default
                ));
            }
        }
    }
}