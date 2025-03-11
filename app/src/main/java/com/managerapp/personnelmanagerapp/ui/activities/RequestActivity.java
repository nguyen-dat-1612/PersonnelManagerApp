package com.managerapp.personnelmanagerapp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.managerapp.personnelmanagerapp.databinding.ActivityRequestBinding;
import com.managerapp.personnelmanagerapp.domain.model.RequestList;
import com.managerapp.personnelmanagerapp.ui.adapters.RequestListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RequestActivity extends AppCompatActivity {

    private ActivityRequestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());


        List<RequestList> requestLists = new ArrayList<>();
        requestLists.add(new RequestList(1, "Đơn xin nghỉ phép"));
        requestLists.add(new RequestList(2, "Đơn xin công tác"));
        requestLists.add(new RequestList(3, "Đơn xin ứng lương"));

        RequestListAdapter adapter = new RequestListAdapter(this, requestLists);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestList request = requestLists.get(position);

                Intent intent = new Intent(RequestActivity.this, DetailsRequestActitivy.class);
                intent.putExtra("request_id", request.getMaLoaiDon());
                startActivity(intent);

            }
        });




        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("key", "value"); // Thêm dữ liệu vào Intent nếu cần
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });




    }
}