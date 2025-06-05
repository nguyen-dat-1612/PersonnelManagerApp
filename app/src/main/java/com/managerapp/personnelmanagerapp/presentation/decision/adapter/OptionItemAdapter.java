package com.managerapp.personnelmanagerapp.presentation.decision.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.managerapp.personnelmanagerapp.R;

import java.util.ArrayList;
import java.util.List;

public class OptionItemAdapter extends ArrayAdapter<OptionItem> {
    private final LayoutInflater inflater;
    private final List<OptionItem> items;

    public OptionItemAdapter(@NonNull Context context) {
        super(context, R.layout.spinner_item, new ArrayList<>());
        this.inflater = LayoutInflater.from(context);
        this.items = new ArrayList<>();
        setDropDownViewResource(R.layout.spinner_dropdown_item);
    }


    public void submitList(List<OptionItem> newItems) {
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        clear();
        addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public OptionItem getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView != null ? convertView :
                inflater.inflate(R.layout.spinner_item, parent, false);

        TextView label = view.findViewById(R.id.text1);
        OptionItem item = getItem(position);
        if (item != null) {
            label.setText(item.getLabel());
            Log.d("Selected: ", item.getLabel() + "");
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView != null ? convertView :
                inflater.inflate(R.layout.spinner_dropdown_item, parent, false);

        TextView label = view.findViewById(R.id.text1);
        OptionItem item = getItem(position);
        if (item != null) {
            label.setText(item.getLabel());
        }

        return view;
    }
}
