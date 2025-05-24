package com.managerapp.personnelmanagerapp.presentation.sendNotification.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.domain.model.Department;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {

    private final List<Department> departments = new ArrayList<>();
    private final Consumer<Department> addListener;

    private final Consumer<Department> removeListener;

    public DepartmentAdapter(Consumer<Department> addListener, Consumer<Department> removeListener) {
        this.addListener = addListener;
        this.removeListener = removeListener;
    }

    public void setDepartments(List<Department> list) {
        departments.clear();
        departments.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department, parent, false);
        return new DepartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
        Department department = departments.get(position);
        holder.bind(department);
    }

    @Override
    public int getItemCount() {
        return departments.size();
    }

    class DepartmentViewHolder extends RecyclerView.ViewHolder {

        private final CheckedTextView checkedTextView;

        public DepartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            checkedTextView = itemView.findViewById(R.id.checkedTextViewDepartment);
        }

        public void bind(Department department) {
            checkedTextView.setText(department.getName());
            checkedTextView.setOnClickListener(v -> {
                if (checkedTextView.isChecked()) {
                    checkedTextView.setChecked(false);
                    removeListener.accept(department);
                } else {
                    checkedTextView.setChecked(true);
                    addListener.accept(department);
                }
            });
        }
    }
}
