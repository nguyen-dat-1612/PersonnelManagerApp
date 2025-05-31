package com.managerapp.personnelmanagerapp.presentation.report.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.managerapp.personnelmanagerapp.domain.model.Cell;
import com.managerapp.personnelmanagerapp.domain.model.ColumnHeader;
import com.managerapp.personnelmanagerapp.domain.model.RowHeader;
import com.managerapp.personnelmanagerapp.R;
public class MyTableAdapter extends AbstractTableAdapter<ColumnHeader, RowHeader, Cell> {

    public MyTableAdapter() {
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateCellViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_cell, parent, false);
        return new CellViewHolder(view);
    }

    @Override
    public void onBindCellViewHolder(@NonNull AbstractViewHolder holder, @NonNull Cell cellItemModel, int columnPosition, int rowPosition) {
    Cell cell = (Cell) cellItemModel;
        CellViewHolder viewHolder = (CellViewHolder) holder;
        viewHolder.cellTextView.setText(cell.getData());
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_column_header, parent, false);
        return new ColumnHeaderViewHolder(view);
    }

    @Override
    public void onBindColumnHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable ColumnHeader columnHeaderItemModel, int columnPosition) {
        ColumnHeader header = (ColumnHeader) columnHeaderItemModel;
        ColumnHeaderViewHolder viewHolder = (ColumnHeaderViewHolder) holder;
        viewHolder.columnTextView.setText(header.getData());
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_row_header, parent, false);
        return new RowHeaderViewHolder(view);
    }

    @Override
    public void onBindRowHeaderViewHolder(@NonNull AbstractViewHolder holder, @Nullable RowHeader rowHeaderItemModel, int rowPosition) {
        RowHeader header = (RowHeader) rowHeaderItemModel;
        RowHeaderViewHolder viewHolder = (RowHeaderViewHolder) holder;
        viewHolder.rowTextView.setText(header.getData());
    }

    @NonNull
    @Override
    public View onCreateCornerView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.table_corner_view, null);
    }

    static class CellViewHolder extends AbstractViewHolder {
        TextView cellTextView;

        public CellViewHolder(@NonNull View itemView) {
            super(itemView);
            cellTextView = itemView.findViewById(R.id.cell_data);
        }
    }

    static class ColumnHeaderViewHolder extends AbstractViewHolder {
        TextView columnTextView;

        public ColumnHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            columnTextView = itemView.findViewById(R.id.column_header);
        }
    }

    static class RowHeaderViewHolder extends AbstractViewHolder {
        TextView rowTextView;

        public RowHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            rowTextView = itemView.findViewById(R.id.row_header);
        }
    }
}
