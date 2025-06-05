package com.managerapp.personnelmanagerapp.presentation.decision.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DecisionItemAdapter extends ArrayAdapter<DecisionItem> {

    private final Context context;
    private final List<DecisionItem> items;
    public DecisionItemAdapter(@NonNull Context context, List<DecisionItem> items) {
        super(context, android.R.layout.simple_spinner_dropdown_item, items);
        this.context = context;
        this.items = items;
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view;
        textView.setText(context.getString(items.get(position).getLabelResId()));
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView textView = (TextView) view;
        textView.setText(context.getString(items.get(position).getLabelResId()));
        return view;
    }

}
