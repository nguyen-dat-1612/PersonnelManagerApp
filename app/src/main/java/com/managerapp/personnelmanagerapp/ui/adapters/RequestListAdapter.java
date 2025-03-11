package com.managerapp.personnelmanagerapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.domain.model.RequestList;

import java.util.List;

public class RequestListAdapter extends BaseAdapter {
    private Context context;
    private List<RequestList> requestLists;

    public RequestListAdapter(Context context, List<RequestList> requestLists) {
        this.context = context;
        this.requestLists = requestLists;
    }

    @Override
    public int getCount() {
        return requestLists.size();
    }

    @Override
    public Object getItem(int position) {
        return requestLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return requestLists.get(position).getMaLoaiDon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_request, parent, false);
        }

        TextView txtOrderName = convertView.findViewById(R.id.requestNameTx);
        RequestList requestList = requestLists.get(position);
        txtOrderName.setText(requestList.getTenLoaiDon());

        return convertView;
    }
}
